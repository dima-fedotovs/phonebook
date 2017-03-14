package lv.tele2.javaschool.phonebook;

import asg.cliche.Command;
import asg.cliche.Param;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Dimitrijs Fedotovs <a href="http://www.bug.guru">www.bug.guru</a>
 * @version 1.0
 * @since 1.0
 */
public class PhoneBook implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Record> recordList = new ArrayList<>();

    @Command(abbrev = "c", name = "create", description = "Creates new record")
    public void create(
            @Param(name = "name", description = "Record's name") String name,
            @Param(name = "phone", description = "Phone number") String... phones) {
        Record r = new Record(name, phones);
        recordList.add(r);
    }

    @Command(abbrev = "l", name = "list", description = "Lists all records")
    public List<Record> list() {
        return recordList;
    }

    @Command
    public void remove(int id, String name) {
        for(Record r : recordList) {
            if (r.getId() == id && r.getName().equals(name)) {
                recordList.remove(r);
                break;
            }
        }
    }

}
