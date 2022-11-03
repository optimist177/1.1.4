package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection conn = Util.getConnect();
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = conn.createStatement()) {
            statement.execute("CREATE TABLE if NOT EXISTS `users` (\n" +
                    "  `id` int NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` varchar(45) NOT NULL,\n" +
                    "  `lastName` varchar(45) NOT NULL,\n" +
                    "  `age` int NOT NULL,\n" +
                    "  PRIMARY KEY (`id`)\n" +
                    ") ");
        } catch (SQLException e) { }

    }

    public void dropUsersTable() {
        try (Statement statement = conn.createStatement()) {
            statement.execute("DROP TABLE if EXISTS users");
        } catch (SQLException e) { }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO users(name,lastName,age) VALUES (?,?,?);")) {
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,lastName);
            preparedStatement.setByte(3,age);
            preparedStatement.executeUpdate();
            System.out.println("User " + name + " добавлен в базу данных ");
        } catch (SQLException e) { }
    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM users WHERE id = ?")) {
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) { }
    }

    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        try (Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users;");
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                allUsers.add(user);
            }
            System.out.println(allUsers);
        } catch (SQLException e) { }
        return allUsers;
    }

    public void cleanUsersTable() {
        try (Statement statement = conn.createStatement()) {
            statement.execute("TRUNCATE users;");
        } catch (SQLException e) { }
    }
}
