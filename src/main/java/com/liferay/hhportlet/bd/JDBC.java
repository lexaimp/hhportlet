package com.liferay.hhportlet.bd;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.*;

public class JDBC {
    private static final String url = "jdbc:mysql://localhost:3306/lportal";
    private static final String user = "luser";
    private static final String password = "luser";

    public static Statement getStatement() throws SQLException {
        Connection connection = DriverManager.getConnection(url, user, password);
        return connection.createStatement();
    }

    public static PreparedStatement getPreparedStatement(String query) throws SQLException {
        Connection connection = DriverManager.getConnection(url, user, password);
        return connection.prepareStatement(query);
    }

//    public void createVacancy(String name, String employerName, Date publishedAt) {
//        try (Statement statement = getStatement()) {
//            statement.executeUpdate("INSERT INTO Vacancies" +
//                    "(name, employerName, publishedAt) " +
//                    "VALUES ('" + name + "','" + employerName + "','" + publishedAt + "')");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public User getUserByName(String name) {
//        try (Statement statement = getStatement()) {
//            ResultSet resultSet = statement.executeQuery(
//                    "SELECT u.id, u.name, u.password, u.date_created, at.damage, at.life, at.rating " +
//                            "FROM users u " +
//                            "JOIN attributes at ON at.user_id = u.id " +
//                            "WHERE name = '" + name + "'");
//            if (resultSet.next()) {
//                User user = new User();
//                user.setId(resultSet.getInt("id"));
//                user.setName(resultSet.getString("name"));
//                user.setPassword(resultSet.getString("password"));
//                user.setDamage(resultSet.getInt("damage"));
//                user.setLife(resultSet.getInt("life"));
//                user.setRating(resultSet.getInt("rating"));
//                return user;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    public void getAttributes() {
    }
}
