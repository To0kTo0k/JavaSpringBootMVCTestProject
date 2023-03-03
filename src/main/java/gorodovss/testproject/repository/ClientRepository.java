package gorodovss.testproject.repository;

import gorodovss.testproject.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Интерфейс Spring Data с набором стандартных методов JPA для работы с БД
 * При необходимости, можно добавить пользовательские методы для взаимодействия с БД
 **/
@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    List<Client> findClientsByStatus(String status);
    List<Client> findClientByUnixtimeGreaterThanEqual(Long unixtime);
}
