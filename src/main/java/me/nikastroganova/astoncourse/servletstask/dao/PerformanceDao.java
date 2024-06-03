package me.nikastroganova.astoncourse.servletstask.dao;

import me.nikastroganova.astoncourse.servletstask.entity.PerformanceEntity;
import me.nikastroganova.astoncourse.servletstask.exception.DaoException;
import me.nikastroganova.astoncourse.servletstask.util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PerformanceDao implements Dao<Integer, PerformanceEntity> {

    private static final PerformanceDao INSTANCE = new PerformanceDao();

    private static final String FIND_ALL_SQL = """
            SELECT id, name, duration, intermission, requisite, hall_id
            FROM performances
            """;
    private static final String DELETE_SQL = """
            DELETE FROM performances
            WHERE id = ?
            """;
    private static final String SAVE_SQL = """
            INSERT INTO performances (id, name, duration, intermission, requisite, hall_id) 
            VALUES (?, ?, ?, ?, ?, ?)
            """;
    private static final String UPDATE_SQL = """
            UPDATE performances
            SET name = ?,
                duration = ?,
                intermission = ?,
                requisite = ?,
                hall_id = ?
            WHERE id = ?
            """;
    private static final String FIND_BY_ID_SQL = """
            SELECT id, name, duration, intermission, requisite, hall_id
            FROM performances
            WHERE id = ?
            """;

    private PerformanceDao(){
    }

    public static PerformanceDao getInstance() {
        return INSTANCE;
    }

    @Override
    public List<PerformanceEntity> findAll() {
        try(var connection = ConnectionManager.getConnection();
            var preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            var resultSet = preparedStatement.executeQuery();
            List<PerformanceEntity> performances = new ArrayList<>();
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
    public Optional<PerformanceEntity> findById(Integer id) {
        try(var connection = ConnectionManager.getConnection();
            var preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);

            var resultSet = preparedStatement.executeQuery();
            PerformanceEntity performanceEntity = null;
            if (resultSet.next()){
                performanceEntity = buildPerformance(resultSet);
            }

            return Optional.ofNullable(performanceEntity);
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
    public void update(PerformanceEntity performanceEntity) {
        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, performanceEntity.getName());
            preparedStatement.setInt(2, performanceEntity.getDuration());
            preparedStatement.setString(3, performanceEntity.getIntermission());
            preparedStatement.setString(4, performanceEntity.getRequisite());
            preparedStatement.setInt(5, performanceEntity.getHallId());
            preparedStatement.setInt(6, performanceEntity.getId());

            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new DaoException(e);
        }
    }

        @Override
        public PerformanceEntity save(PerformanceEntity performanceEntity) {
        try(var connection = ConnectionManager.getConnection();
            var preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, performanceEntity.getId());
            preparedStatement.setString(2, performanceEntity.getName());
            preparedStatement.setInt(3, performanceEntity.getDuration());
            preparedStatement.setString(4, performanceEntity.getIntermission());
            preparedStatement.setString(5, performanceEntity.getRequisite());
            preparedStatement.setInt(6, performanceEntity.getHallId());

            preparedStatement.executeUpdate();

            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next())
                performanceEntity.setId(generatedKeys.getInt("id"));
            return performanceEntity;
        }
        catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private PerformanceEntity buildPerformance(ResultSet resultSet) throws SQLException {
        return new PerformanceEntity(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getInt("duration"),
                resultSet.getString("intermission"),
                resultSet.getString("requisite"),
                resultSet.getInt("hall_id")
        );
    }
}
