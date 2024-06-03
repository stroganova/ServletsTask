package me.nikastroganova.astoncourse.servletstask.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PerformanceEntity {
    private Integer id;
    private String name;
    private Integer duration;
    private String intermission;
    private String requisite;
    private Integer hallId;
}
