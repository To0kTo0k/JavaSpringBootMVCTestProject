package gorodovss.testproject.dtos;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ClientDto {
    private String image;
    @Pattern(regexp = "[A-Z][a-z]+(\\s[A-Z][a-z]+)(\\s[A-Z][a-z]+)?")
    @NotBlank
    private String name;
    @Email
    @NotBlank
    private String email;
    @Positive
    private int age;
    @NotBlank
    @Pattern(regexp = "(fe)?(male)")
    private String sex;
    private LocalDate birthday;
}
