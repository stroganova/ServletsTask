package me.nikastroganova.astoncourse.servletstask.exception;

public class DaoException extends RuntimeException{

    public DaoException(Throwable throwable) {
        super(throwable);
    }
}
