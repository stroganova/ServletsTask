package me.nikastroganova.astoncourse.servletstask.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Performance {
    private Integer id;
    private String name;
    private String description;
    private Hall hall;
    private List<Actor> actors;
}
