package lv.tele2.javaschool.phonebook;

import asg.cliche.ShellFactory;

import java.io.*;

/**
 * @author Dimitrijs Fedotovs <a href="http://www.bug.guru">www.bug.guru</a>
 * @version 1.0
 * @since 1.0
 */
public class Main {
    private static Database database;

    public static void main(String[] args) {
        try (Database db = new Database("myphonesdb")) {
            database = db;
            PhoneBook phoneBook = new PhoneBook();
            ShellFactory.createConsoleShell("book", null, phoneBook)
                    .commandLoop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Database getDatabase() {
        return database;
    }
}
