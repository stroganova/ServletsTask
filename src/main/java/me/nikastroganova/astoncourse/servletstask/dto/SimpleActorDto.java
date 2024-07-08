package me.nikastroganova.astoncourse.servletstask.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SimpleActorDto {
    private final Integer id;
    private final String firstName;
    private final String lastName;
    private final String phoneNumber;
}
