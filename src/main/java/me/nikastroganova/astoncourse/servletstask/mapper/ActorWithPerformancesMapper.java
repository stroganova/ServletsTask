package me.nikastroganova.astoncourse.servletstask.mapper;

import me.nikastroganova.astoncourse.servletstask.dto.ActorDtoWithPerformances;
import me.nikastroganova.astoncourse.servletstask.dto.SimplePerformanceDto;
import me.nikastroganova.astoncourse.servletstask.entity.Actor;
import me.nikastroganova.astoncourse.servletstask.entity.Performance;

import java.util.List;
import java.util.stream.Collectors;

public class ActorWithPerformancesMapper implements Mapper<Actor, ActorDtoWithPerformances> {

    private static final ActorWithPerformancesMapper INSTANCE = new ActorWithPerformancesMapper();

    @Override
    public ActorDtoWithPerformances toDto(Actor entity) {
        SimplePerformanceMapper performanceMapper = SimplePerformanceMapper.getInstance();
        List<SimplePerformanceDto> performancesDto = entity.getPerformances().stream()
                .map(performanceMapper::toDto)
                .collect(Collectors.toList());

        return ActorDtoWithPerformances.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .performances(performancesDto)
                .build();
    }

    @Override
    public Actor toEntity(ActorDtoWithPerformances dto) {
        SimplePerformanceMapper performanceMapper = SimplePerformanceMapper.getInstance();
        List<Performance> performances = dto.getPerformances().stream()
                    .map(performanceMapper::toEntity)
                    .collect(Collectors.toList());

        return new Actor(
                dto.getId(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getPhoneNumber(),
                performances
        );

    }

    public static ActorWithPerformancesMapper getInstance() {
        return INSTANCE;
    }

    private ActorWithPerformancesMapper() {}
}