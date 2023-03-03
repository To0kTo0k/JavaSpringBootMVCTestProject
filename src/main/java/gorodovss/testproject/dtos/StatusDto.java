package gorodovss.testproject.dtos;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class StatusDto {
    @Pattern(regexp = "O(N|FF)LINE")
    private String status;
}
