package me.nikastroganova.astoncourse.servletstask.exception;

public class DatabaseConnectionException extends RuntimeException {

    public DatabaseConnectionException(Throwable throwable){
        super(throwable);
    }
}
