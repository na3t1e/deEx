package ru.learnSchool.program.entityManager;

import ru.learnSchool.App;
import ru.learnSchool.program.entity.EntityServices;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class EntityManagerServices {
    public static void insert(EntityServices services) throws SQLException {
        try (Connection c = App.getConnection()) {
            String s = "INSERT INTO SERVICE (Title,Cost, Duration,  DurationInUnit, Description,  Discount, MainImagePath) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement p = c.prepareStatement(s, Statement.RETURN_GENERATED_KEYS);
            p.setString(1, services.getTitle());
            p.setDouble(2, services.getCost());
            p.setInt(3, services.getDuration());
            p.setString(4, services.getUnit());
            p.setString(5, services.getDescription());
            p.setDouble(6, services.getDiscount());
            p.setString(7, services.getPath());
            p.executeUpdate();
            ResultSet keys = p.getGeneratedKeys();
            if (keys.next()) {
                services.setId(keys.getInt(1));
                return;
            }
        }
        throw new SQLException("Услуга не добавлена");
    }

    public static void update(EntityServices services) throws SQLException {
        try (Connection c = App.getConnection()) {
            String s = "UPDATE SERVICE set Title=?,Cost=?, Duration=?,  DurationInUnit=?, Description=?,  Discount=?, MainImagePath=? WHERE ID =?";
            PreparedStatement p = c.prepareStatement(s);
            p.setString(1, services.getTitle());
            p.setDouble(2, services.getCost());
            p.setInt(3, services.getDuration());
            p.setString(4, services.getUnit());
            p.setString(5, services.getDescription());
            p.setDouble(6, services.getDiscount());
            p.setString(7, services.getPath());
            p.setInt(8, services.getId());
            try {
                p.executeUpdate();
            } catch (SQLException e) {
                throw new SQLException("Услуга не обновлена");
            }
        }
    }

    public static List<EntityServices> all() throws SQLException {
        try (Connection c = App.getConnection()) {
            String s = "SELECT * FROM SERVICE";
            Statement statement = c.createStatement();
            ResultSet result = statement.executeQuery(s);
            List<EntityServices> list = new ArrayList<>();
            while (result.next()) {
                list.add(new EntityServices(
                        result.getInt(1),
                        result.getString(2),
                        result.getDouble(3),
                        result.getInt(4),
                        result.getString(5),
                        result.getDouble(6),
                        result.getString(7),
                        result.getString(8)
                ));
            }
            return list;
        }
    }

    public static void delete(EntityServices services) throws SQLException {
        try (Connection c = App.getConnection()) {
            String s = "DELETE FROM SERVICE WHERE ID =?";
            PreparedStatement p = c.prepareStatement(s);
            p.setInt(1, services.getId());
            p.executeUpdate();
        }
    }
}
