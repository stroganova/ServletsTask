package me.nikastroganova.astoncourse.servletstask.service;

import me.nikastroganova.astoncourse.servletstask.dao.impl.ActorDaoImpl;
import me.nikastroganova.astoncourse.servletstask.dto.SimpleActorDto;
import me.nikastroganova.astoncourse.servletstask.dto.ActorDtoWithPerformances;
import me.nikastroganova.astoncourse.servletstask.entity.Actor;
import me.nikastroganova.astoncourse.servletstask.mapper.ActorWithPerformancesMapper;
import me.nikastroganova.astoncourse.servletstask.mapper.SimpleActorMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class ActorService {

    private static final ActorService INSTANCE = new ActorService();

    private static final ActorDaoImpl actorDao = ActorDaoImpl.getInstance();
    private static final SimpleActorMapper simpleActorMapper = SimpleActorMapper.getInstance();
    private static final ActorWithPerformancesMapper actorWithPerformancesMapper = ActorWithPerformancesMapper.getInstance();

    public static SimpleActorDto save(SimpleActorDto actorDto) {
        Actor actor = actorDao.save(simpleActorMapper.toEntity(actorDto));
        return simpleActorMapper.toDto(actor);
    }

    public static void update(SimpleActorDto actorDto) {
        actorDao.update(simpleActorMapper.toEntity(actorDto));
    }

    public void deleteById(int id) {
        actorDao.delete(id);
    }

    public Optional<ActorDtoWithPerformances> findById(int id) {
        Optional<Actor> actor = actorDao.findById(id);
        if (actor.isPresent()) {
             return actor.map(actorWithPerformancesMapper::toDto);
        }
        return Optional.empty();
    }

    public List<ActorDtoWithPerformances> findAll() {
        return actorDao.findAll().stream()
                .map(actorWithPerformancesMapper::toDto)
                .collect(Collectors.toList());
    }

    public static ActorService getInstance(){
        return INSTANCE;
    }

    private ActorService() {
    }


}
