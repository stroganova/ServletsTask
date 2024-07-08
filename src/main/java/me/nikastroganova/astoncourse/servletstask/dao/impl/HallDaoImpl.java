package me.nikastroganova.astoncourse.servletstask.dao.impl;

import me.nikastroganova.astoncourse.servletstask.dao.Dao;
import me.nikastroganova.astoncourse.servletstask.entity.Hall;
import me.nikastroganova.astoncourse.servletstask.exception.DaoException;
import me.nikastroganova.astoncourse.servletstask.util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HallDaoImpl implements Dao<Integer, Hall> {

    private static final HallDaoImpl INSTANCE = new HallDaoImpl();

    private static final String DELETE_SQL = """
        DELETE FROM halls
        WHERE id = ?
        """;
    private static final String UPDATE_SQL = """
        UPDATE halls
        SET hall_name = ?,
            hall_address = ?,
            hall_phone = ?
        WHERE id = ?
        """;
    private static final String SAVE_SQL = """
        INSERT INTO halls (hall_name, hall_address, hall_phone)
        VALUES (?, ?, ?)
        """;
    private static final String FIND_BY_ID_SQL = """
        SELECT id, hall_name, hall_address, hall_phone
        FROM halls
        WHERE id = ?
        """;
    private static final String FIND_ALL_SQL = """
        SELECT id, hall_name, hall_address, hall_phone
        FROM halls
        """;

    private HallDaoImpl(){
    }

    public static HallDaoImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Hall> findAll() {
        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            var resultSet = preparedStatement.executeQuery();
            List<Hall> halls = new ArrayList<>();
            while(resultSet.next()) {
                halls.add(buildHall(resultSet));
            }
            return halls;
        }
        catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Hall> findById(Integer id) {
        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);

            var resultSet = preparedStatement.executeQuery();

            Hall hall = null;
            if(resultSet.next()) {
                hall = buildHall(resultSet);
            }
            return Optional.ofNullable(hall);
        }
        catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Integer id) {
        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        }
        catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(Hall hall) {
        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, hall.getName());
            preparedStatement.setString(2, hall.getAddress());
            preparedStatement.setString(3, hall.getPhoneNumber());
            preparedStatement.setInt(4, hall.getId());

            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Hall save(Hall hall) {
        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, hall.getName());
            preparedStatement.setString(2, hall.getAddress());
            preparedStatement.setString(3, hall.getPhoneNumber());

            preparedStatement.executeUpdate();

            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next())
                hall.setId(generatedKeys.getInt("id"));
            return hall;
        }
        catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean isExist(Integer id) {
        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);
            var resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                return true;
            }
            return false;
        }
        catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Hall buildHall(ResultSet resultSet) throws SQLException {
        return new Hall(
                resultSet.getInt("id"),
                resultSet.getString("hall_name"),
                resultSet.getString("hall_address"),
                resultSet.getString("hall_phone")
        );
    }

}
