package me.nikastroganova.astoncourse.servletstask.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PerformanceDto {
    Integer id;
    String name;
}
