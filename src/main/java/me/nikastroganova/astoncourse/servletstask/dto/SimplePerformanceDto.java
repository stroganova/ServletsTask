package me.nikastroganova.astoncourse.servletstask.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SimplePerformanceDto {
    Integer id;
    String name;
    String description;
    Integer hallId;
}
