package me.nikastroganova.astoncourse.servletstask.mapper;

public interface Mapper<F, T> {
    T toDto (F entity);
    F toEntity (T dto);
}
