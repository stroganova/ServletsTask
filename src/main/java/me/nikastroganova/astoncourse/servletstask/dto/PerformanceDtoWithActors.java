package me.nikastroganova.astoncourse.servletstask.dto;

import lombok.Builder;
import lombok.Value;
import me.nikastroganova.astoncourse.servletstask.entity.Hall;

import java.util.List;

@Value
@Builder
public class PerformanceDtoWithActors {
    Integer id;
    String name;
    String description;
    HallDto hall;
    List<SimpleActorDto> actors;
}
