package gorodovss.testproject.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.time.LocalDate;

/**
 * Класс Клиент
 * Реализует собой представление записи таблицы clients БД в PostgreSQL
 * Содержит информацию о клиенте (URI аватарки, ФИО, email, возраст, пол, дата рождения)
 * Также, реализована выдача уникального ID клиента, статус пользователя по умолчанию ONLINE, метка времени добавления пользователя
 *
 * @author Sergey Gorodov
 **/
@Entity
@Table(name = "clients")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "status")
    private String status = "ONLINE";
    @Column(name = "image")
    private String image;
    @Column(name = "username")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "age")
    private int age;
    @Column(name = "sex")
    private String sex;
    @Column(name = "birthday")
    private LocalDate birthday;
    @Column(name = "unixtime")
    private Long unixtime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Client client = (Client) o;
        return false;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
