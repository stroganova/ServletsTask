package me.nikastroganova.astoncourse.servletstask.dao.impl;

import me.nikastroganova.astoncourse.servletstask.dao.Dao;
import me.nikastroganova.astoncourse.servletstask.entity.Actor;
import me.nikastroganova.astoncourse.servletstask.entity.Hall;
import me.nikastroganova.astoncourse.servletstask.entity.Performance;
import me.nikastroganova.astoncourse.servletstask.exception.DaoException;
import me.nikastroganova.astoncourse.servletstask.util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ActorDaoImpl implements Dao<Integer, Actor> {

    private static final ActorDaoImpl INSTANCE = new ActorDaoImpl();

    private static final String DELETE_SQL = """
            DELETE FROM actors
            WHERE id = ?   
            """;
    private static final String SAVE_SQL = """
            INSERT INTO actors (firstname, lastname, phone)
            VALUES (?, ?, ?)   
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
    private static final String FIND_PERFORMANCES_BY_ACTOR_SQL = """
            SELECT performances.id, name, description, hall_id, hall_name, hall_address, hall_phone
            FROM performances
            JOIN actors_performances AS ap
            ON id = ap.performance_id
            JOIN halls
            ON hall_id = halls.id
            WHERE ap.actor_id = ?
            """;

    private ActorDaoImpl() {
    }

    public static ActorDaoImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Actor> findAll() {
        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            var resultSet = preparedStatement.executeQuery();
            List<Actor> actors = new ArrayList<>();
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
    public Optional<Actor> findById(Integer id) {
        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);
            var resultSet = preparedStatement.executeQuery();
            Actor actor = null;
            if (resultSet.next()) {
                actor = buildActor(resultSet);
            }
            return Optional.ofNullable(actor);
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
    public void update(Actor actor) {
        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, actor.getFirstName());
            preparedStatement.setString(2, actor.getLastName());
            preparedStatement.setString(3, actor.getPhoneNumber());
            preparedStatement.setInt(4, actor.getId());

            preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            throw new DaoException(e);
        }
    }

    @Override
    public Actor save(Actor actor) {
        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, actor.getFirstName());
            preparedStatement.setString(2, actor.getLastName());
            preparedStatement.setString(3, actor.getPhoneNumber());

            preparedStatement.executeUpdate();

            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next())
                actor.setId(generatedKeys.getInt("id"));
            return actor;
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
            if (resultSet.next()) {
                return true;
            }
            return false;
        }
        catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<Performance> findPerformancesByActor(int actorID) {
        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(FIND_PERFORMANCES_BY_ACTOR_SQL)) {
            preparedStatement.setInt(1, actorID);
            var resultSet = preparedStatement.executeQuery();
            List<Performance> performances = new ArrayList<>();
            while(resultSet.next()) {
                Hall hall = new Hall(
                        resultSet.getInt("hall_id"),
                        resultSet.getString("hall_name"),
                        resultSet.getString("hall_address"),
                        resultSet.getString("hall_phone"));

                Performance performance  = new Performance(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        hall,
                        Collections.EMPTY_LIST);

                performances.add(performance);
            }
            return performances;
        }
        catch (SQLException e) {
            throw new DaoException(e);
        }

    }

    private Actor buildActor(ResultSet resultSet) throws SQLException {
        int actorID = resultSet.getInt("id");

        return new Actor(
                actorID,
                resultSet.getString("firstname"),
                resultSet.getString("lastname"),
                resultSet.getString("phone"),
                findPerformancesByActor(actorID)
                );
    }

}
