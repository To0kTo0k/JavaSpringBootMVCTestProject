package gorodovss.testproject;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import gorodovss.testproject.storage.StorageService;
import gorodovss.testproject.storage.StorageProperties;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class TestprojectApplication {

	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			//storageService.deleteAll();
			storageService.init();
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(TestprojectApplication.class, args);
	}
}
