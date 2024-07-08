package me.nikastroganova.astoncourse.servletstask.service;

import me.nikastroganova.astoncourse.servletstask.dao.impl.HallDaoImpl;
import me.nikastroganova.astoncourse.servletstask.dto.HallDto;
import me.nikastroganova.astoncourse.servletstask.entity.Hall;
import me.nikastroganova.astoncourse.servletstask.mapper.HallMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class HallService {

    private static final HallService INSTANCE = new HallService();

    private static final HallDaoImpl hallDao = HallDaoImpl.getInstance();
    private static final HallMapper hallMapper = HallMapper.getInstance();

    public HallDto save(HallDto hallDto) {
        Hall hall = hallDao.save(hallMapper.toEntity(hallDto));
        return hallMapper.toDto(hall);
    }

    public static void update(HallDto hallDto) {
        hallDao.update(hallMapper.toEntity(hallDto));
    }

    public List<HallDto> findAll() {
        List<HallDto> halls = hallDao.findAll().stream()
                .map(hallMapper::toDto)
                .collect(Collectors.toList());
        return halls;
    }

    public Optional<HallDto> findById(int id) {
        Optional<Hall> hall = hallDao.findById(id);
        if (hall.isPresent()) {
            return hall.map(hallMapper::toDto);
        }
        return Optional.empty();
    }

    public void deleteById(int id) {
        hallDao.delete(id);
    }

    public static HallService getInstance(){
        return INSTANCE;
    }

    private HallService(){
    }
}
