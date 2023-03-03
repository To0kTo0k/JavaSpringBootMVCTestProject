package gorodovss.testproject.advice;

import gorodovss.testproject.dtos.Response;
import gorodovss.testproject.exceptions.ClientOldStatusEqualNewStatusException;
import gorodovss.testproject.exceptions.NotDeletedClientException;
import gorodovss.testproject.exceptions.NotFoundClientsException;
import gorodovss.testproject.storage.StorageFileNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Контроллер для обработки исключений в одном месте
 **/
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    /**
     * Ни один клиент из списка всех клиентов не удовлетворяет поисковым требованиям
     **/
    @ExceptionHandler(NotFoundClientsException.class)
    public ResponseEntity<Response> handleNotFoundClientsException(NotFoundClientsException e) {
        Response response = new Response(e.getMessage(), "Сделайте запрос с другими параметрами. Объекты с заданными параметрами не обнаружены");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Переопределенный обработчик, отвечающий за исключение о нечитаемом теле запроса
     **/
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Response response = new Response("Некорректный JSON", ex.getMessage());
        return new ResponseEntity<>(response, status);
    }

    /**
     * Файл не найден
     **/
    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

    /**
     * Клиент не удален из БД
     **/
    @ExceptionHandler(NotDeletedClientException.class)
    public ResponseEntity<Response> handleNotDeletedClientException() {
        Response response = new Response("Запись о клиенте не удалилась");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Старый статус равен новому
     **/
    @ExceptionHandler(ClientOldStatusEqualNewStatusException.class)
    public ResponseEntity<Response> handleClientOldStatusEqualNewStatusException() {
        Response response = new Response("Старый и новый статус клиента равны", "Введите другой статус, для редактирования записи о клиенте");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
