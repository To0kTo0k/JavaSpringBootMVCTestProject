package gorodovss.testproject.dtos;

import lombok.Data;

@Data
public class StatusResponseDto {
    private int id;
    private String oldStatus;
    private String newStatus;
}

