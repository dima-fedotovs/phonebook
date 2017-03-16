package lv.tele2.javaschool.phonebook;

import asg.cliche.Command;
import asg.cliche.Param;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Dimitrijs Fedotovs <a href="http://www.bug.guru">www.bug.guru</a>
 * @version 1.0
 * @since 1.0
 */
public class PhoneBook  {

    @Command(abbrev = "c", name = "create", description = "Creates new record")
    public void create(
            @Param(name = "name", description = "Record's name") String name,
            @Param(name = "phone", description = "Phone number") String... phones) {
        Record r = new Record(name, phones);
        r.insert();
    }

    @Command(abbrev = "l", name = "list", description = "Lists all records")
    public List<Record> list() throws SQLException {
        return Record.findAll();
    }

    @Command
    public void remove(int id, String name) {
        // TODO implement this
    }

    @Command
    public void generate(int count) {
        for (int i = 0; i < count; i++) {
            generate();
        }
    }

    @Command
    public void generate() {
        JSONObject obj = callNameFake();
        String name = obj.getString("name");
        String phone = obj.getString("phone_h");
        create(name, phone);
    }

    private JSONObject callNameFake() {
        try {
            URL url = new URL("http://api.namefake.com/english-uk/random");
            try (InputStream is = url.openStream()) {
                JSONTokener t = new JSONTokener(is);
                JSONObject obj = new JSONObject(t);
                return obj;
            }
        } catch (IOException e) {
            return null;
        }
    }

}
