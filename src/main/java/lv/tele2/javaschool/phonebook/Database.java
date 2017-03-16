package lv.tele2.javaschool.phonebook;

import org.apache.derby.jdbc.EmbeddedDriver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Dimitrijs Fedotovs <a href="http://www.bug.guru">www.bug.guru</a>
 * @version 1.0
 * @since 1.0
 */
public class Database implements AutoCloseable {
    private static final String DB_URL_TEMPLATE = "jdbc:derby:%s;create=true";
    private Connection connection;

    public Database(String name) throws SQLException {
        new EmbeddedDriver();
        String dburl = String.format(DB_URL_TEMPLATE, name);
        connection = DriverManager.getConnection(dburl);
        createTables();
    }

    private void createTables() throws SQLException{
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("create table record (" +
                    "id int not null primary key, " +
                    "name varchar(100) not null, " +
                    "phones varchar(1000))");
            System.out.println("table created");
        } catch (SQLException e) {
            if (!e.getMessage().contains("already exists")) {
                throw e;
            }
        }
    }

    public Connection getConnection() {
        return connection;
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
