package me.nikastroganova.astoncourse.servletstask.dao;

import me.nikastroganova.astoncourse.servletstask.entity.ActorEntity;
import me.nikastroganova.astoncourse.servletstask.entity.HallEntity;
import me.nikastroganova.astoncourse.servletstask.exception.DaoException;
import me.nikastroganova.astoncourse.servletstask.util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HallDao implements Dao<Integer, HallEntity>{

    private static final HallDao INSTANCE = new HallDao();

    private static final String DELETE_SQL = """
        DELETE FROM halls
        WHERE id = ?
        """;
    private static final String UPDATE_SQL = """
        UPDATE halls
        SET name = ?,
            address = ?,
            phone = ?,
            manager_name = ?
        WHERE id = ?
        """;
    private static final String SAVE_SQL = """
        INSERT INTO halls (id, name, address, phone, manager_name)
        VALUES (?, ?, ?, ?, ?)
        """;
    private static final String FIND_BY_ID_SQL = """
        SELECT id, name, address, phone, manager_name
        FROM halls
        WHERE id = ?
        """;
    private static final String FIND_ALL_SQL = """
        SELECT id, name, address, phone, manager_name
        FROM halls
        """;

    private HallDao(){
    }

    public HallDao getInstance() {
        return INSTANCE;
    }

    @Override
    public List<HallEntity> findAll() {
        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            var resultSet = preparedStatement.executeQuery();
            List<HallEntity> halls = new ArrayList<>();
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
    public Optional<HallEntity> findById(Integer id) {
        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);

            var resultSet = preparedStatement.executeQuery();

            HallEntity hallEntity = null;
            if(resultSet.next()) {
                hallEntity = buildHall(resultSet);
            }
            return Optional.ofNullable(hallEntity);
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
    public void update(HallEntity hallEntity) {
        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, hallEntity.getName());
            preparedStatement.setString(2, hallEntity.getAddress());
            preparedStatement.setString(3, hallEntity.getPhoneNumber());
            preparedStatement.setString(4, hallEntity.getManagerName());
            preparedStatement.setInt(5, hallEntity.getId());

            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public HallEntity save(HallEntity hallEntity) {
        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, hallEntity.getId());
            preparedStatement.setString(2, hallEntity.getName());
            preparedStatement.setString(3, hallEntity.getAddress());
            preparedStatement.setString(4, hallEntity.getPhoneNumber());
            preparedStatement.setString(5, hallEntity.getManagerName());

            preparedStatement.executeUpdate();

            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next())
                hallEntity.setId(generatedKeys.getInt("id"));
            return hallEntity;
        }
        catch (SQLException e) {
            throw new DaoException(e);
        }

    }

    private HallEntity buildHall(ResultSet resultSet) throws SQLException {
        return new HallEntity(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("address"),
                resultSet.getString("phone"),
                resultSet.getString("manager_name")
        );
    }

}
