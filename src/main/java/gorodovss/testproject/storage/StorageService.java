package gorodovss.testproject.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * Интерфейс StorageService
 * Указывает, какие методы будут использоваться реализации действий над загружаемыми файлами
 *
 * @author Spring Guides <a href="https://github.com/spring-guides">...</a>
 * @see FileSystemStorageService
 **/
public interface StorageService {

    /**
     * Инициализация файловой системы сервера
     **/
    void init();

    /**
     * Сохраняет загружаемый файл в файловой системе
     *
     * @param file - загружаемый на сервер файл
     **/
    void store(MultipartFile file);

    /**
     * @return возвращает поток с деревом файлов
     **/
    Stream<Path> loadAll();

    /**
     * @return возвращает путь к файлу
     **/
    Path load(String filename);

    /**
     * Выгружает файл по ссылке
     *
     * @param filename - URI-ссылка на файл
     * @return возвращает файл как ресурс (выгружает файл на устройство клиента)
     **/
    Resource loadAsResource(String filename);

    /**
     * Очистка содержимого файловой системы
     **/
    void deleteAll();

}