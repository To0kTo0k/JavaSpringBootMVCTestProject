package gorodovss.testproject.dtos;

import lombok.Data;


@Data
public class StatisticDto {
    private int id;
    private String name;
    private String image;
    private String status;
    private Long unixtime;
}
