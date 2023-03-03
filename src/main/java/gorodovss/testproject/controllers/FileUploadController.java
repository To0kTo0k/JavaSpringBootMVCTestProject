package gorodovss.testproject.controllers;

import gorodovss.testproject.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.stream.Collectors;

/**
 * Класс контроллер FileUploadController
 * Определяет HTTP-методы, обрабатываемые сервисом (спасибо @Controller)
 * Методы относятся к URL-адресу "/"
 *
 * @author Spring Guides <a href="https://github.com/spring-guides">...</a>
 **/
@Controller
public class FileUploadController {

    private final StorageService storageService;

    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    /**
     * Обработка get-запроса
     *
     * @param model
     * @return возвращает форму с функциями загрузки/выгрузки файлов и вывода URI содержимого сервера
     **/
    @GetMapping("/")
    public String listUploadedFiles(Model model) throws IOException {

        model.addAttribute("files", storageService.loadAll().map(
                        path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                                "serveFile", path.getFileName().toString()).build().toUri().toString())
                .collect(Collectors.toList()));

        return "uploadForm";
    }

    /**
     * Обработка get-запроса
     * Загрузка выбранного файла на устройство клиента
     *
     * @param filename - URI-ссылка на файл
     * @return возвращает файл и http-ответ с кодом состояния
     **/
    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    /**
     * Обработка post-запроса
     * Загрузка файла на сервер
     *
     * @param file - файл, загружаемый на сервер
     **/
    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        storageService.store(file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/";
    }
}