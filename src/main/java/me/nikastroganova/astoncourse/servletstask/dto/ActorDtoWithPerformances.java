package me.nikastroganova.astoncourse.servletstask.dto;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public final class ActorDtoWithPerformances {
    private final Integer id;
    private final String firstName;
    private final String lastName;
    private final String phoneNumber;
    private final List<SimplePerformanceDto> performances;

}
