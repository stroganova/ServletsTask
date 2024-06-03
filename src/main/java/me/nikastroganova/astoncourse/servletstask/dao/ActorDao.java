package me.nikastroganova.astoncourse.servletstask.dao;

import lombok.SneakyThrows;
import me.nikastroganova.astoncourse.servletstask.entity.ActorEntity;
import me.nikastroganova.astoncourse.servletstask.exception.DaoException;
import me.nikastroganova.astoncourse.servletstask.util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ActorDao implements Dao<Integer, ActorEntity>{

    private static final ActorDao INSTANCE = new ActorDao();

    private static final String DELETE_SQL = """
            DELETE FROM actors
            WHERE id = ?   
            """;
    private static final String SAVE_SQL = """
            INSERT INTO actors (id, firstname, lastname, phone)
            VALUES (?, ?, ?, ?)   
            """;
    private static final String UPDATE_SQL = """
            UPDATE actors
            SET firstname = ?,
                lastname = ?,
                phone = ?
            WHERE id = ?
            """;
    private static final String FIND_BY_ID_SQL = """
            SELECT id, firstname, lastname, phone
            FROM actors
            WHERE id = ?   
            """;
    private static final String FIND_ALL_SQL = """
            SELECT id, firstname, lastname, phone
            FROM actors
            """;

    private ActorDao() {
    }

    public ActorDao getInstance() {
        return INSTANCE;
    }

    @Override
    public List<ActorEntity> findAll() {
        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            var resultSet = preparedStatement.executeQuery();
            List<ActorEntity> actors = new ArrayList<>();
            while(resultSet.next()) {
                actors.add(buildActor(resultSet));
            }
            return actors;
        }
        catch (SQLException e) {
            throw new DaoException(e);
        }

    }

    @Override
    public Optional<ActorEntity> findById(Integer id) {
        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);
            var resultSet = preparedStatement.executeQuery();
            ActorEntity actorEntity = null;
            if (resultSet.next()) {
                actorEntity = buildActor(resultSet);
            }
            return Optional.ofNullable(actorEntity);
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
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(ActorEntity actorEntity) {
        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, actorEntity.getFirstName());
            preparedStatement.setString(2, actorEntity.getLastName());
            preparedStatement.setString(3, actorEntity.getPhoneNumber());
            preparedStatement.setInt(4, actorEntity.getId());

            preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            throw new DaoException(e);
        }
    }

    @Override
    public ActorEntity save(ActorEntity actorEntity) {
        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, actorEntity.getId());
            preparedStatement.setString(2, actorEntity.getFirstName());
            preparedStatement.setString(3, actorEntity.getLastName());
            preparedStatement.setString(1, actorEntity.getPhoneNumber());

            preparedStatement.executeUpdate();

            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next())
                actorEntity.setId(generatedKeys.getInt("id"));
            return actorEntity;
        }
        catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private ActorEntity buildActor(ResultSet resultSet) throws SQLException {
        return new ActorEntity(
                resultSet.getInt("id"),
                resultSet.getString("firstname"),
                resultSet.getString("lastname"),
                resultSet.getString("phone")
                );
    }

}
