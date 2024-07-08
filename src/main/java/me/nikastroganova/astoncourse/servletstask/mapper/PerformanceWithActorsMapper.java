package me.nikastroganova.astoncourse.servletstask.mapper;

import me.nikastroganova.astoncourse.servletstask.dto.HallDto;
import me.nikastroganova.astoncourse.servletstask.dto.PerformanceDtoWithActors;
import me.nikastroganova.astoncourse.servletstask.dto.SimpleActorDto;
import me.nikastroganova.astoncourse.servletstask.entity.Actor;
import me.nikastroganova.astoncourse.servletstask.entity.Hall;
import me.nikastroganova.astoncourse.servletstask.entity.Performance;

import java.util.List;
import java.util.stream.Collectors;

public class PerformanceWithActorsMapper implements Mapper<Performance, PerformanceDtoWithActors> {

    private static final PerformanceWithActorsMapper INSTANCE = new PerformanceWithActorsMapper();

    @Override
    public PerformanceDtoWithActors toDto(Performance entity) {
        SimpleActorMapper actorMapper = SimpleActorMapper.getInstance();
        HallMapper hallMapper = HallMapper.getInstance();

        List<SimpleActorDto> actors = entity.getActors().stream()
                .map(actorMapper::toDto)
                .collect(Collectors.toList());
        HallDto hall = hallMapper.toDto(entity.getHall());

        return PerformanceDtoWithActors.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .hall(hall)
                .actors(actors)
                .build();
    }

    @Override
    public Performance toEntity(PerformanceDtoWithActors dto) {
        SimpleActorMapper actorMapper = SimpleActorMapper.getInstance();
        HallMapper hallMapper = HallMapper.getInstance();

        List<Actor> actors = dto.getActors().stream()
                .map(actorMapper::toEntity)
                .collect(Collectors.toList());

        Hall hall = hallMapper.toEntity(dto.getHall());

        return new Performance(dto.getId(), dto.getName(), dto.getDescription(), hall, actors);
    }

    public static PerformanceWithActorsMapper getInstance() {
        return INSTANCE;
    }

    private PerformanceWithActorsMapper() {}
}
