package me.nikastroganova.astoncourse.servletstask.service;

import me.nikastroganova.astoncourse.servletstask.dao.impl.PerformanceDaoImpl;
import me.nikastroganova.astoncourse.servletstask.dto.PerformanceDtoWithActors;
import me.nikastroganova.astoncourse.servletstask.dto.SimplePerformanceDto;
import me.nikastroganova.astoncourse.servletstask.entity.Actor;
import me.nikastroganova.astoncourse.servletstask.entity.Performance;
import me.nikastroganova.astoncourse.servletstask.mapper.PerformanceWithActorsMapper;
import me.nikastroganova.astoncourse.servletstask.mapper.SimplePerformanceMapper;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.*;

public class PerformanceService {

    private static final PerformanceService INSTANCE = new PerformanceService();

    private static final PerformanceDaoImpl performanceDao = PerformanceDaoImpl.getInstance();
    private static final PerformanceWithActorsMapper performanceWithActorsMapper = PerformanceWithActorsMapper.getInstance();
    private static final SimplePerformanceMapper simplePerformanceMapper = SimplePerformanceMapper.getInstance();

    public static SimplePerformanceDto save(SimplePerformanceDto performanceDto) {
        Performance performance = performanceDao.save(simplePerformanceMapper.toEntity(performanceDto));
        return simplePerformanceMapper.toDto(performance);
    }

    public static void update(SimplePerformanceDto performanceDto) {
        performanceDao.update(simplePerformanceMapper.toEntity(performanceDto));
    }

    public Optional<PerformanceDtoWithActors> findById(int id) {
        Optional<Performance> performance = performanceDao.findById(id);
        if (performance.isPresent()) {
            return performance.map(performanceWithActorsMapper::toDto);
        }
        return Optional.empty();
    }

    public void deleteById(int id) {
        performanceDao.delete(id);
    }

    public List<PerformanceDtoWithActors> findAll() {
        return performanceDao.findAll().stream()
                .map(performanceWithActorsMapper::toDto)
                .collect(toList());
    }

    public void addActorToPerformance(int actorId, int performanceId) {
        performanceDao.addActorToPerformance(actorId, performanceId);
    }

    public void deleteActorFromPerformance(int actorId, int performanceId) {
        performanceDao.deleteActorFromPerformance(actorId, performanceId);
    }

    public static PerformanceService getInstance() {
        return INSTANCE;
    }

    private PerformanceService() {}

}
