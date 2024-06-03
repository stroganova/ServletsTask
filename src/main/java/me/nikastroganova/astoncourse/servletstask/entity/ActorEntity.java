package me.nikastroganova.astoncourse.servletstask.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ActorEntity {
    private Integer id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
}
