package gorodovss.testproject.utils;

import gorodovss.testproject.dtos.ClientDto;
import gorodovss.testproject.dtos.StatisticDto;
import gorodovss.testproject.dtos.StatusDto;
import gorodovss.testproject.dtos.StatusResponseDto;
import gorodovss.testproject.entity.Client;
import org.springframework.stereotype.Service;

@Service
public class MappingUtils {
    /**
     * Конвертация entity в dto
     **/
    public StatisticDto mapToStatisticDto(Client client) {
        StatisticDto dto = new StatisticDto();
        dto.setId(client.getId());
        dto.setName(client.getName());
        dto.setStatus(client.getStatus());
        dto.setImage(client.getImage());
        dto.setUnixtime(client.getUnixtime());
        return dto;
    }

    public StatusResponseDto mapToStatusResponseDto(Client client, String status) {
        StatusResponseDto dto = new StatusResponseDto();
        dto.setId(client.getId());
        dto.setOldStatus(client.getStatus());
        dto.setNewStatus(status);
        return dto;
    }

    /**
     * Конвертация dto в entity
     **/
    public Client mapToClient(Client client, ClientDto clientDto) {
        client.setImage(clientDto.getImage());
        client.setName(clientDto.getName());
        client.setEmail(clientDto.getEmail());
        client.setAge(clientDto.getAge());
        client.setSex(clientDto.getSex());
        client.setBirthday(clientDto.getBirthday());
        return client;
    }

    /**
     * Конвертация dto в String
     **/
    public String mapFromStatusDto(StatusDto statusDto) {
        return statusDto.getStatus();
    }
}
