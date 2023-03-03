package gorodovss.testproject.controllers;

import gorodovss.testproject.dtos.ClientDto;
import gorodovss.testproject.dtos.StatisticDto;
import gorodovss.testproject.dtos.StatusDto;
import gorodovss.testproject.dtos.StatusResponseDto;
import gorodovss.testproject.entity.Client;
import gorodovss.testproject.exceptions.ClientOldStatusEqualNewStatusException;
import gorodovss.testproject.exceptions.NotDeletedClientException;
import gorodovss.testproject.exceptions.NotFoundClientsException;
import gorodovss.testproject.repository.ClientRepository;
import gorodovss.testproject.service.ClientService;
import gorodovss.testproject.utils.MappingUtils;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Класс контроллер ClientController
 * Определяет HTTP-методы, обрабатываемые сервисом (спасибо @Controller в составе @RestController)
 * Методы в контроллере относятся к URL-адресу "/"
 * Ответы сервера автоматически сериализуются в JSON (спасибо @ResponseBody в составе @RestController)
 *
 * @author Sergey Gorodov
 **/
@RestController
@RequestMapping("/")
@Log
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ClientService clientService;
    @Autowired
    private MappingUtils mappingUtils;

    /**
     * Обработка post-запроса
     * Добавление текущего времени в виде unix
     * Создание записи о новом клиенте в БД
     *
     * @param clientDto - данные о клиенте в JSON-формате, конвертируются в объект класса Client
     * @return - уникальный ID созданной в БД записи и код-состояние
     **/
    @PostMapping(value = "/clients")
    public ResponseEntity<?> create(@Validated @RequestBody ClientDto clientDto) {
        // при инициализации создается ID и статус пользователя
        Client client = new Client();
        // добавляется персональная информация
        client = mappingUtils.mapToClient(client, clientDto);
        // добавляется текущее время в виде unix
        client.setUnixtime(Instant.now().getEpochSecond());
        clientService.create(client);
        return new ResponseEntity<>(client.getId(), HttpStatus.CREATED);
    }

    /**
     * Обработка get-запроса
     * Вывод записей БД о клиентах
     *
     * @return - список объектов класса Client и код-состояние или исключение
     **/
    @GetMapping(value = "/clients")
    public ResponseEntity<List<Client>> read() throws NotFoundClientsException {
        final List<Client> clients = clientService.readAll();

        if (clients != null && !clients.isEmpty()) {
            return new ResponseEntity<>(clients, HttpStatus.OK);
        } else {
            throw new NotFoundClientsException("Клиенты не найдены");
        }
    }

    /**
     * Обработка get-запроса
     * Вывод записи БД о конкретном клиенте
     *
     * @param id - ID конкретного клиента в БД (получается через захват определенной части из URI)
     * @return - объект класса Client и код-состояние или исключение
     **/
    @GetMapping(value = "/clients/{id}")
    public ResponseEntity<Client> read(@PathVariable(name = "id") int id) throws NotFoundClientsException {
        final Client client = clientService.read(id);

        if (client != null) {
            return new ResponseEntity<>(client, HttpStatus.OK);
        } else {
            throw new NotFoundClientsException("Клиент с данным ID не найден");
        }
    }

    /**
     * Обработка put-запроса
     * Изменение статуса клиента
     *
     * @param id     - ID конкретного клиента в БД
     * @param statusDto - новый статус клиента
     * @return - возвращает объект класса с ID, новым и старым статусом клиента и код-состояние или исключение
     **/
    @PutMapping(value = "/clients/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") int id, @RequestBody StatusDto statusDto) throws NotFoundClientsException, ClientOldStatusEqualNewStatusException {
        if (clientService.isExist(id)) {
            Client client = clientService.read(id);
            String status = mappingUtils.mapFromStatusDto(statusDto);
            if (!Objects.equals(client.getStatus(), status)) {
                StatusResponseDto statusResponseDto = mappingUtils.mapToStatusResponseDto(client, status);
                client.setUnixtime(Instant.now().getEpochSecond());
                client.setStatus(status);
                clientRepository.save(client);
                return new ResponseEntity<>(statusResponseDto, HttpStatus.OK);
            } else {
                throw new ClientOldStatusEqualNewStatusException();
            }
        }
        throw new NotFoundClientsException("Клиент с данным ID не найден");
    }

    /**
     * Обработка delete-запроса
     * Удаление записи БД о конкретном клиенте
     *
     * @param id - ID конкретного клиента в БД (получается через захват определенной части из URI)
     * @return - код-состояние или исключение
     **/
    @DeleteMapping(value = "/clients/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) throws NotDeletedClientException {
        final boolean deleted = clientService.delete(id);

        if (deleted) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            throw new NotDeletedClientException();
        }
    }

    /**
     * Обработка get-запроса
     * Вывод списка клиентов с определенным статусом
     *
     * @param status - статус клиентов в БД (получается через захват определенной части из URI)
     * @return - список объектов класса Client, содержащих требуемое значение в поле status, и код-состояние или исключение
     **/
    @GetMapping(value = "/clients/status/{status}")
    public ResponseEntity<?> serverStatusStat(@PathVariable(name = "status") String status) throws NotFoundClientsException {
        List<Client> clients = clientService.readByStatus(status);
        if (clients.isEmpty()) {
            throw new NotFoundClientsException("Клиенты с требуемым статусом не найдены");
        } else {
            List<StatisticDto> statisticDtos = new ArrayList<>();
            for (Client client : clients) {
                StatisticDto statisticDto = mappingUtils.mapToStatisticDto(client);
                statisticDtos.add(statisticDto);
            }
            return new ResponseEntity<>(statisticDtos, HttpStatus.OK);
        }
    }

    /**
     * Обработка get-запроса
     * Вывод списка клиентов, созданных или измененных после определенного времени
     *
     * @param unixtime - метка времени
     * @return - список объектов класса Client, содержащих значение в поле status большее или равное требуемому, и код состояния или исключение
     **/
    @GetMapping(value = "/clients/timestamp/{unixtime}")
    public ResponseEntity<?> serverUnixtimeStat(@PathVariable(name = "unixtime") Long unixtime) throws NotFoundClientsException {
        List<Client> clients = clientService.readByUnixtime(unixtime);
        if (clients.isEmpty()) {
            throw new NotFoundClientsException("Клиенты в требуемом временном отрезке не найдены");
        } else {
            List<StatisticDto> statisticDtos = new ArrayList<>();
            for (Client client : clients) {
                StatisticDto statisticDto = mappingUtils.mapToStatisticDto(client);
                statisticDtos.add(statisticDto);
            }
            return new ResponseEntity<>(statisticDtos, HttpStatus.OK);
        }
    }
}
