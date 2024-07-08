package me.nikastroganova.astoncourse.servletstask.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class HallDto {
    Integer id;
    String name;
    String address;
    String phoneNumber;
}
