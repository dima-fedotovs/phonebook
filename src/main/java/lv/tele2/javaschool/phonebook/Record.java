package lv.tele2.javaschool.phonebook;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Dimitrijs Fedotovs <a href="http://www.bug.guru">www.bug.guru</a>
 * @version 1.0
 * @since 1.0
 */
public class Record implements Serializable {
    private static final long serialVersionUID = 1L;

    private static int nextId = 1;

    private int id;
    private String name;
    private List<String> phoneList = new ArrayList<>();

    public Record() {
        this.id = nextId;
        nextId++;
    }

    public Record(String name, String... phones) {
        this();
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

    private void readObject(java.io.ObjectInputStream in)
            throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        nextId = Math.max(id + 1, nextId);
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", id, name, phoneList);
    }
}
