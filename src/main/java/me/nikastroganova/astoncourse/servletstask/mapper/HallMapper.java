package me.nikastroganova.astoncourse.servletstask.mapper;

import me.nikastroganova.astoncourse.servletstask.dto.HallDto;
import me.nikastroganova.astoncourse.servletstask.entity.Hall;

public class HallMapper implements Mapper<Hall, HallDto> {

    private static final HallMapper INSTANCE = new HallMapper();

    @Override
    public HallDto toDto(Hall entity) {
        return HallDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .address(entity.getAddress())
                .phoneNumber(entity.getPhoneNumber())
                .build();
    }

    @Override
    public Hall toEntity(HallDto dto) {
        return new Hall(
                dto.getId(),
                dto.getName(),
                dto.getAddress(),
                dto.getPhoneNumber()
        );
    }

    public static HallMapper getInstance() {
        return INSTANCE;
    }

    private HallMapper() {}
}
