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
    private final Connection connection;
    private static final String URL_TEMPL = "jdbc:derby:%s;create=true";

    public Database(String name) throws SQLException {
        String url = String.format(URL_TEMPL, name);
        new EmbeddedDriver();
        connection = DriverManager.getConnection(url);
        createTableRecord();
        createTablePhone();
    }

    private void createTablePhone() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("create table phone(" +
                    "record_id int not null references record(id)," +
                    "phone varchar(100) not null)");
        } catch (SQLException e) {
            if (!e.getMessage().contains("already exists")) {
                throw e;
            }
        }
    }

    private void createTableRecord() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("create table record (" +
                    "id int not null primary key, " +
                    "name varchar(100) not null)");
        } catch (SQLException e) {
            if (!e.getMessage().contains("already exists")) {
                throw e;
            }
        }
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }

    public Connection getConnection() {
        return connection;
    }
}
