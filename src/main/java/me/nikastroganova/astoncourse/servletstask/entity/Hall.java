package me.nikastroganova.astoncourse.servletstask.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Hall {
    private Integer id;
    private String name;
    private String address;
    private String phoneNumber;
}
