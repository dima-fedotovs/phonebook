package lv.tele2.javaschool.phonebook;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Dimitrijs Fedotovs <a href="http://www.bug.guru">www.bug.guru</a>
 * @version 1.0
 * @since 1.0
 */
public class Record {
    private int id;
    private String name;
    private List<String> phoneList = new ArrayList<>();

    public Record(String name, String... phones) {
        this.name = name;
        Collections.addAll(this.phoneList, phones);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPhoneList() {
        return phoneList;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", id, name, phoneList);
    }

    public void save() throws SQLException {
        Connection con = Main.getDatabase().getConnection();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("select max(id) from record")) {
            rs.next();
            int maxId = rs.getInt(1);
            id = maxId + 1;
        }
        try (PreparedStatement stmtRec
                     = con.prepareStatement("insert into record (id, name) values (?, ?)");
             PreparedStatement stmtPhone
                     = con.prepareStatement("insert into phone (record_id, phone) values (?, ?)")) {
            stmtRec.setInt(1, id);
            stmtRec.setString(2, name);
            stmtRec.executeUpdate();

            stmtPhone.setInt(1, id);

            for (String p : phoneList) {
                stmtPhone.setString(2, p);
                stmtPhone.executeUpdate();
            }
        }
    }

    public static List<Record> findAll() throws SQLException {
        List<Record> result = new ArrayList<>();
        Connection con = Main.getDatabase().getConnection();
        try (Statement stmt = con.createStatement();
             ResultSet rs
                     = stmt.executeQuery("select * from record")) {
            while (rs.next()) {
                Record r = construct(rs);
                result.add(r);
            }
        }
        return result;
    }

    private static Record construct(ResultSet rs) throws SQLException {
        Connection con = Main.getDatabase().getConnection();
        int id = rs.getInt("id");
        String name = rs.getString("name");
        Record r = new Record(name);
        r.id = id;
        try (PreparedStatement stmt = con.prepareStatement("select * from phone where record_id = ?")) {
            stmt.setInt(1, id);
            try (ResultSet rsPhone = stmt.executeQuery()) {
                while (rsPhone.next()) {
                    String phone = rsPhone.getString("phone");
                    r.phoneList.add(phone);
                }
            }
        }
        return r;
    }

    public static void remove(int id) throws SQLException {
        Connection con = Main.getDatabase().getConnection();
        try (PreparedStatement stmtPhone = con.prepareStatement("delete from phone where record_id = ?");
             PreparedStatement stmtRec = con.prepareStatement("delete from record where id = ?")) {
            stmtPhone.setInt(1, id);
            stmtPhone.executeUpdate();
            stmtRec.setInt(1, id);
            stmtRec.executeUpdate();
        }

    }
}
