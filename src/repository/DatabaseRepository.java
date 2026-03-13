package repository;

import domain.Identifiable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class DatabaseRepository<T extends Identifiable<ID>, ID> implements IRepository<T, ID> {
    protected String databaseUrl;
    protected Connection connection = null;

    public DatabaseRepository(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }

    public void openConnection() {
        try {
            if (this.connection != null && !this.connection.isClosed())
                return;

            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection(databaseUrl);
        } catch (SQLException | ClassNotFoundException exception) {
            throw new RuntimeException("Error connecting to database: " + exception.getMessage(), exception);
        }
    }

    public void closeConnection() throws SQLException {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Error closing connection: " + exception.getMessage(), exception);
        }
    }
}