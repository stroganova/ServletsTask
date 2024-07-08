package me.nikastroganova.astoncourse.servletstask.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Actor {
    private Integer id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private List<Performance> performances;
}
