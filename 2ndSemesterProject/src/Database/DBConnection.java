package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
    private Connection connection = null;
    private static DBConnection dbConnection;

    private static final String driverClass = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String serverAddress = "hildur.ucn.dk";
    private static final int serverPort = 1433;
    private static final String password = "Password1!";

    private ConnectionEnvironment env;

    private DBConnection(ConnectionEnvironment env) {
        this.env = env;

        String connectionString = String.format("jdbc:sqlserver://%s:%d;databaseName=%s;user=%s;password=%s;encrypt=false",
                serverAddress, serverPort, env.getDatabaseName(), env.getUserName(), password);
        try {
            connection = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            System.err.println("Could not connect to database " + env.getDatabaseName() + "@" + serverAddress + ":" + serverPort + " as user " + env.getUserName() + " using password ******");
            System.out.println("Connection string was: " + connectionString.substring(0, connectionString.length() - password.length()) + "....");
            e.printStackTrace();
        }
    }

    public static DBConnection getInstance(ConnectionEnvironment env) {
        if (dbConnection == null) {
            dbConnection = new DBConnection(env);
        }
        return dbConnection;
    }

    public static DBConnection getInstance() {
        if (dbConnection == null) {
            dbConnection = new DBConnection(ConnectionEnvironment.PRODUCTION);
        }
        return dbConnection;
    }

    public void startTransaction() throws SQLException {
        connection.setAutoCommit(false);
    }

    public void commitTransaction() throws SQLException {
        connection.commit();
        connection.setAutoCommit(true);
    }

    public void rollbackTransaction() throws SQLException {
        connection.rollback();
        connection.setAutoCommit(true);
    }

    public int executeUpdate(String sql) throws SQLException {
        System.out.println("DBConnection, Updating: " + sql);
        int res = -1;
        try (Statement s = connection.createStatement()) {
            res = s.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return res;
    }


    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                // Reinitialize the connection
                String connectionString = String.format(
                        "jdbc:sqlserver://%s:%d;databaseName=%s;user=%s;password=%s;encrypt=false",
                        serverAddress, serverPort, env.getDatabaseName(), env.getUserName(), password);
                connection = DriverManager.getConnection(connectionString);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    public void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
