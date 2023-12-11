package Database;

import Model.Janitor;
import Model.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO {

    ConnectionEnvironment env;

    public TaskDAO(ConnectionEnvironment env) {
        this.env = env;
    }

    public boolean saveTask(Task task) {

        String insertTaskQuery = "INSERT INTO Task (priority, [description], deadline, completed, receptionistId, janitorId) VALUES (?, ?, ?, ?, ?, ?);";

        int result = 0;

        try (Connection connection = DBConnection.getConnection(env)) {
            PreparedStatement preparedStatement = connection.prepareStatement(insertTaskQuery);

            preparedStatement.setInt(1, task.getPriority());
            preparedStatement.setNString(2, task.getDescription());
            preparedStatement.setDate(3, task.getDeadline());
            preparedStatement.setBoolean(4, task.isCompleted());
            preparedStatement.setInt(5, task.getReceptionist().getId());
            preparedStatement.setInt(6, task.getJanitor().getId());

            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result > 0;
    }
}
