package Database;

import java.sql.*;

public class DBConnection {

    private static final String serverAddress = "hildur.ucn.dk";
    private static final int serverPort = 1433;
    private static final String password = "Password1!";

    public static Connection getConnection(ConnectionEnvironment env) {
        Connection connection;

        try {
            String connectionString = String.format(
                    "jdbc:sqlserver://%s:%d;databaseName=%s;user=%s;password=%s;encrypt=false",
                    serverAddress, serverPort, env.getDatabaseName(), env.getUserName(), password);
            connection = DriverManager.getConnection(connectionString);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    public static void startTransaction(Connection connection) throws SQLException {
        connection.setAutoCommit(false);
    }

    public static void startTransaction(Connection connection, int isolationLevel) throws SQLException {
        connection.setAutoCommit(false);

        if (isValidTransactionIsolation(isolationLevel)) {
            connection.setTransactionIsolation(isolationLevel);
        } else {
            connection.setAutoCommit(true);
            throw new IllegalArgumentException("Unsupported isolation level: " + isolationLevel);
        }

    }

    private static boolean isValidTransactionIsolation(int isolationLevel) {
        // Check if the provided isolation level is a valid constant according to java.sql.Connection
        return isolationLevel == Connection.TRANSACTION_NONE ||
                isolationLevel == Connection.TRANSACTION_READ_UNCOMMITTED ||
                isolationLevel == Connection.TRANSACTION_READ_COMMITTED ||
                isolationLevel == Connection.TRANSACTION_REPEATABLE_READ ||
                isolationLevel == Connection.TRANSACTION_SERIALIZABLE;
    }

    public static void commitTransaction(Connection connection) throws SQLException {
        connection.commit();
        connection.setAutoCommit(true);
    }

    public static void rollbackTransaction(Connection connection) throws SQLException {
        connection.rollback();
        connection.setAutoCommit(true);
    }

    public static void executeUpdateWithIdentityInsert(Connection connection, String query, String columnName) throws SQLException {

        String setIdentityInsertOn = "SET IDENTITY_INSERT " + columnName + " ON";
        String setIdentityInsertOff = "SET IDENTITY_INSERT " + columnName + " OFF";
        try {
            Statement statement = connection.createStatement();

            statement.executeUpdate(setIdentityInsertOn);
            statement.executeUpdate(query);
            statement.executeUpdate(setIdentityInsertOff);

            statement.close();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    public static int executeUpdate(Connection connection, String query) throws SQLException {
        int result = 0;
        try (Statement statement = connection.createStatement();) {
            result = statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException(e);
        }
        return result;
    }

    public static ResultSet executeQuery(Connection connection, String query) {
        ResultSet resultSet = null;
        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }


    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
