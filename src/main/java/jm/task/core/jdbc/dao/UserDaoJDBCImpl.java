package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate("CREATE TABLE user (" +
                    "id INT NOT NULL AUTO_INCREMENT," +
                    "username VARCHAR(100) NOT NULL, " +
                    "userfirst_name VARCHAR(100) NOT NULL, " +
                    "userage INT NOT NULL, " +
                    "PRIMARY KEY(id));");
        } catch (SQLException e) {
            System.out.println("Таблица уже создана.");
        }
    }

    public void dropUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate("DROP TABLE user");
        } catch (SQLException e) {
            System.out.println("Удаление таблицы не удалось. Возможно таблица используется или уже удалена");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate("INSERT INTO user (username, userfirst_name, userage) " +
                    "VALUES('" + name + "', '" + lastName + "', " + age + ");");
        } catch (SQLException e) {
            // System.out.println("Удаление таблицы не удалось. Возможно таблица используется или уже удалена");
            System.out.println(e.getErrorCode());
            e.getNextException();
            e.getSQLState();
        }
    }

    public void removeUserById(long id) {
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate("DELETE FROM user where (id = " + id + ");");
            System.out.println("Deleted index " + id);
        } catch (SQLException e) {
            System.out.println(e.getSQLState() + "Произошла ошибка удаления");
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try (Statement statement = Util.getConnection().createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT * FROM user;");
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong(1));
                user.setName(rs.getString(2));
                user.setLastName(rs.getString(3));
                user.setAge(rs.getByte(4));
                list.add(user);
            }
        } catch (SQLException e) {
            System.err.println(e.getSQLState());
        }
        return list;
    }

    public void cleanUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate("DELETE FROM user;");
        } catch (SQLException e) {
            System.out.println(e.getMessage() + "\n" + e.getErrorCode() + "Произошла ошибка очистки");
        }
    }
}
