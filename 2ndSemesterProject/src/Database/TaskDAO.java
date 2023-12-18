package Database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.Address;
import Model.Task;
import Model.Receptionist;

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

    public boolean finishTask(int id) {
        String finishTaskQuery = "UPDATE Task SET Completed = 1 WHERE id = ?";

        int result = 0;

        try (Connection connection = DBConnection.getConnection(env)) {
            PreparedStatement preparedStatement = connection.prepareStatement(finishTaskQuery);

            preparedStatement.setInt(1, id);

            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result > 0;
    }

    public ArrayList<Task> getUncompletedTasks(int janitorId, boolean useJanitorId) {
        String getUncompletedTasksQuery = "SELECT task.id, task.[priority], task.[description], task.startDate, task.deadLine, task.receptionistId, "
                + "emp.fname, emp.lname, emp.email, emp.phoneno, emp.cprNo, "
                + "[address].id as addressId, [address].street, [address].streetno, [address].zipcode, city.city "
                + "FROM task "
                + "LEFT JOIN employee emp ON task.receptionistId = emp.id "
                + "LEFT JOIN [address] ON emp.addressId = [address].id "
                + "LEFT JOIN City ON [address].zipcode = city.zipcode "
                + "WHERE completed = 0";

        if (useJanitorId) {
            getUncompletedTasksQuery += " AND JanitorId = ?";
        }


        ArrayList<Task> result = new ArrayList<Task>();

        try (Connection connection = DBConnection.getConnection(env)) {
            PreparedStatement preparedStatement = connection.prepareStatement(getUncompletedTasksQuery);

            if (useJanitorId) {
                preparedStatement.setInt(1, janitorId);
            }
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int priority = rs.getInt("priority");
                String description = rs.getString("description");
                Date startDate = rs.getDate("startDate");
                Date deadLine = rs.getDate("deadLine");
                int receptionistId = rs.getInt("receptionistId");
                String employeeName = rs.getString("fname") + " " + rs.getString("lname");
                String email = rs.getString("email");
                String phoneNumber = rs.getString("phoneno");
                String cpr = rs.getString("cprno");

                int addressId = rs.getInt("addressId");
                String street = rs.getString("street");
                int streetNo = rs.getInt("streetNo");
                int zipCode = rs.getInt("zipcode");
                String city = rs.getString("city");
                Address address = new Address(addressId, street, streetNo, zipCode, city);

                Receptionist receptionist = new Receptionist(receptionistId, employeeName, address, phoneNumber, email,
                        cpr, null);
                result.add(new Task(id, description, priority, deadLine, receptionist, startDate));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
