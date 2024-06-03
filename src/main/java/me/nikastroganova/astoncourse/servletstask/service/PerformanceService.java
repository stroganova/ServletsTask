package me.nikastroganova.astoncourse.servletstask.service;

import me.nikastroganova.astoncourse.servletstask.dao.PerformanceDao;
import me.nikastroganova.astoncourse.servletstask.dto.PerformanceDto;

import java.util.List;

import static java.util.stream.Collectors.*;

public class PerformanceService {

    private static final PerformanceService INSTANCE = new PerformanceService();

    private static final PerformanceDao performanceDao = PerformanceDao.getInstance();

    private PerformanceService(){
    }

    public List<PerformanceDto> findAll() {
        return performanceDao.findAll().stream()
                .map(performance -> PerformanceDto.builder()
                        .id(performance.getId())
                        .name(performance.getName())
                        .build())
                .collect(toList());
    }

    public static PerformanceService getInstance() {
        return INSTANCE;
    }
}
