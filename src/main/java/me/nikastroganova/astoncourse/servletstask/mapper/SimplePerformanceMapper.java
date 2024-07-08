package me.nikastroganova.astoncourse.servletstask.mapper;

import me.nikastroganova.astoncourse.servletstask.dao.impl.HallDaoImpl;
import me.nikastroganova.astoncourse.servletstask.dto.SimplePerformanceDto;
import me.nikastroganova.astoncourse.servletstask.entity.Performance;

import java.util.Collections;

public class SimplePerformanceMapper implements Mapper<Performance, SimplePerformanceDto> {

    private static final SimplePerformanceMapper INSTANCE = new SimplePerformanceMapper();

    @Override
    public SimplePerformanceDto toDto(Performance entity) {
        return SimplePerformanceDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .hallId(entity.getHall().getId())
                .build();
    }

    @Override
    public Performance toEntity(SimplePerformanceDto dto) {
        HallDaoImpl hallDao = HallDaoImpl.getInstance();
        return new Performance(
                dto.getId(),
                dto.getName(),
                dto.getDescription(),
                hallDao.findById(dto.getHallId()).get(),
                Collections.EMPTY_LIST
        );
    }

    public static SimplePerformanceMapper getInstance() {
        return INSTANCE;
    }

    private SimplePerformanceMapper() {}
}
