package gorodovss.testproject.service;

import gorodovss.testproject.entity.Client;
import gorodovss.testproject.service.impl.ClientServiceImpl;

import java.util.List;

/**
 * Интерфейс ClientService
 * Указывает, какие методы будут использоваться для реализации действий над классом Client
 *
 * @author Sergey Gorodov
 * @see Client
 * @see ClientServiceImpl
 **/
public interface ClientService {
    /**
     * Создает нового клиента
     *
     * @param client - клиент для создания
     */
    void create(Client client);

    /**
     * Возвращает список всех имеющихся клиентов
     *
     * @return список клиентов
     */
    List<Client> readAll();

    /**
     * Возвращает клиента по его ID
     *
     * @param id - ID клиента
     * @return - объект клиента с заданным ID
     */
    Client read(int id);

    /**
     * Проверяет наличие клиента с заданным ID
     *
     * @param id - ID клиента
     * @return - true если клиент с заданным ID имеется, иначе false
     **/
    boolean isExist(int id);

    /**
     * Удаляет клиента с заданным ID
     *
     * @param id - ID клиента, которого нужно удалить
     * @return - true если клиент был удален, иначе false
     */
    boolean delete(int id);

    /**
     * Возвращает список клиентов, имеющих требуемый статус
     *
     * @param status - критерий отбора клиентов в список
     * @return - список клиентов
     **/
    List<Client> readByStatus(String status);

    /**
     * Возвращает список клиентов, созданных или обновленных после определенного времени
     *
     * @param unixtime - метка времени
     * @return - список клиентов
     **/
    List<Client> readByUnixtime(Long unixtime);


}
