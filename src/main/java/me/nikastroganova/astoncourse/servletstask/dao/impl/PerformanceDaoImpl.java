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
import java.util.*;


public class PerformanceDaoImpl implements Dao<Integer, Performance> {

    private static final PerformanceDaoImpl INSTANCE = new PerformanceDaoImpl();

    private static final String FIND_ALL_SQL = """
            SELECT id, name, description, hall_id
            FROM performances
            """;
    private static final String DELETE_SQL = """
            DELETE FROM performances
            WHERE id = ?
            """;
    private static final String SAVE_SQL = """
            INSERT INTO performances (name, description, hall_id) 
            VALUES (?, ?, ?)
            """;
    private static final String UPDATE_SQL = """
            UPDATE performances
            SET name = ?,
                description = ?,
                hall_id = ?
            WHERE id = ?
            """;
    private static final String FIND_BY_ID_SQL = """
            SELECT id, name, description, hall_id
            FROM performances
            WHERE id = ?
            """;
    private static final String FIND_HALL_BY_PERFORMANCE_SQL = """
            SELECT halls.id, hall_name, hall_address, hall_phone
            FROM halls
            JOIN performances
            ON halls.id  = performances.hall_id
            WHERE performances.id = ?
            """ ;
    private static final String FIND_ACTORS_BY_PERFORMANCE_SQL = """
            SELECT actors.id, firstname, lastname, phone
            FROM actors
            JOIN actors_performances AS ap
            ON actors.id = ap.actor_id
            JOIN performances
            ON performances.id = ap.performance_id
            WHERE performances.id = ?
            """;
    private static final String ADD_ACTOR_SQL = """
          INSERT INTO actors_performances(actor_id, performance_id) 
          VALUES (?, ?)
          """;
    private static final String DELETE_ACTOR_SQL = """
           DELETE FROM actors_performances
           WHERE actor_id = ? AND performance_id = ?
           """;

    private PerformanceDaoImpl(){
    }

    public static PerformanceDaoImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Performance> findAll() {
        try(var connection = ConnectionManager.getConnection();
            var preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            var resultSet = preparedStatement.executeQuery();
            List<Performance> performances = new ArrayList<>();
            while (resultSet.next()) {
                performances.add(buildPerformance(resultSet));
            }
            return performances;
        }
        catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Performance> findById(Integer id) {
        try(var connection = ConnectionManager.getConnection();
            var preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);

            var resultSet = preparedStatement.executeQuery();
            Performance performance = null;
            if (resultSet.next()){
                performance = buildPerformance(resultSet);
            }

            return Optional.ofNullable(performance);
        }
        catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Integer id) {
        try(var connection = ConnectionManager.getConnection();
            var preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        }
        catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(Performance performance) {
        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, performance.getName());
            preparedStatement.setString(2, performance.getDescription());
            preparedStatement.setInt(3, performance.getHall().getId());
            preparedStatement.setInt(4, performance.getId());

            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new DaoException(e);
        }
    }

        @Override
        public Performance save(Performance performance) {
        try(var connection = ConnectionManager.getConnection();
            var preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, performance.getName());
            preparedStatement.setString(2, performance.getDescription());
            preparedStatement.setInt(3, performance.getHall().getId());

            preparedStatement.executeUpdate();

            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next())
                performance.setId(generatedKeys.getInt("id"));
            return performance;
        }
        catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean isExist(Integer id) {
        try(var connection = ConnectionManager.getConnection();
            var preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);
            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
               return true;
            }
            return false;
        }
        catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<Actor> findActorsByPerformance(int performanceID) {
        try(var connection = ConnectionManager.getConnection();
            var preparedStatement = connection.prepareStatement(FIND_ACTORS_BY_PERFORMANCE_SQL)) {
            preparedStatement.setInt(1, performanceID);
            var resultSet = preparedStatement.executeQuery();
            List<Actor> actors = new ArrayList<>();
            while (resultSet.next()) {
                Actor actor = new Actor(
                        resultSet.getInt("id"),
                        resultSet.getString("firstname"),
                        resultSet.getString("lastname"),
                        resultSet.getString("phone"),
                        Collections.EMPTY_LIST
                );
                actors.add(actor);
            }
            return actors;
        }
        catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Hall findHallByPerformance(int performanceID) {
        try(var connection = ConnectionManager.getConnection();
            var preparedStatement = connection.prepareStatement(FIND_HALL_BY_PERFORMANCE_SQL)) {
            preparedStatement.setInt(1, performanceID);
            var resultSet = preparedStatement.executeQuery();
            resultSet.next();
            Hall hall = new Hall(
                        resultSet.getInt("id"),
                        resultSet.getString("hall_name"),
                        resultSet.getString("hall_address"),
                        resultSet.getString("hall_phone")
            );
            return hall;
            }
        catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public void addActorToPerformance(int actorId, int performanceId) {
        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(ADD_ACTOR_SQL)) {
            preparedStatement.setInt(1, actorId);
            preparedStatement.setInt(2, performanceId);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public void deleteActorFromPerformance(int actorId, int performanceId) {
        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(DELETE_ACTOR_SQL)) {
            preparedStatement.setInt(1, actorId);
            preparedStatement.setInt(2, performanceId);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Performance buildPerformance(ResultSet resultSet) throws SQLException {
        var performanceID = resultSet.getInt("id");
        return new Performance(
                performanceID,
                resultSet.getString("name"),
                resultSet.getString("description"),
                findHallByPerformance(performanceID),
                findActorsByPerformance(performanceID)
        );
    }

}
