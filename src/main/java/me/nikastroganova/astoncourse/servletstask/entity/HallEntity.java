package me.nikastroganova.astoncourse.servletstask.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HallEntity {
    private Integer id;
    private String name;
    private String address;
    private String phoneNumber;
    private String managerName;
}
