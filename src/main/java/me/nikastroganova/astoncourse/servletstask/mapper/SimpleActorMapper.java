package me.nikastroganova.astoncourse.servletstask.mapper;

import me.nikastroganova.astoncourse.servletstask.dto.SimpleActorDto;
import me.nikastroganova.astoncourse.servletstask.entity.Actor;

import java.util.Collections;

public class SimpleActorMapper implements Mapper<Actor, SimpleActorDto> {

    private static final SimpleActorMapper INSTANCE = new SimpleActorMapper();

    @Override
    public SimpleActorDto toDto(Actor entity) {
        return SimpleActorDto.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .phoneNumber(entity.getPhoneNumber())
                .build();
    }

    @Override
    public Actor toEntity(SimpleActorDto dto) {
        return new Actor(
                dto.getId(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getPhoneNumber(),
                Collections.EMPTY_LIST
        );
    }

    public static SimpleActorMapper getInstance() {
        return INSTANCE;
    }

    private SimpleActorMapper() {}
}
