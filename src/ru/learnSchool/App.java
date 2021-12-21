package ru.learnSchool;

import ru.learnSchool.program.ui.MainTable;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class App {
    public static void main(String[] args) {
        new MainTable();
        updateFont("Comic Sans");
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/school", "root", "1234");
    }

    public static void updateFont(String font) {
        java.util.Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if(value instanceof javax.swing.plaf.FontUIResource){
                UIManager.put(key, value);
            }
        }
    }
}
