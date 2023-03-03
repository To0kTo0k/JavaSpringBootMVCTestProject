package gorodovss.testproject.service.impl;

import gorodovss.testproject.entity.Client;
import gorodovss.testproject.repository.ClientRepository;
import gorodovss.testproject.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Реализация интерфейса ClientService
 *
 * @author Sergey Gorodov
 * @see Client
 * @see ClientService
 **/
@Service
public class ClientServiceImpl implements ClientService {
    /**
     * Создание объекта, реализующего методы интерфейса ClientRepository
     * Помогает наладить взаимодействие между БД и классом Client
     *
     * @see Client
     * @see ClientRepository
     **/
    @Autowired
    private ClientRepository clientRepository;

    @Override
    public void create(Client client) {
        clientRepository.save(client);
    }

    @Override
    public List<Client> readAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client read(int id) {
        return clientRepository.getReferenceById(id);
    }

    @Override
    public boolean isExist(int id) {
        return clientRepository.existsById(id);
    }

    @Override
    public boolean delete(int id) {
        if (clientRepository.existsById(id)) {
            clientRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Client> readByStatus(String status) {
        return clientRepository.findClientsByStatus(status);
    }

    @Override
    public List<Client> readByUnixtime(Long unixtime) {
        return clientRepository.findClientByUnixtimeGreaterThanEqual(unixtime);
    }
}
