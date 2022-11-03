package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;


public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        Util.getConnect();
        Util.getFactory();
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();

        userService.saveUser("Иван", "Иванов", (byte) 18);
        userService.saveUser("Петр", "Петров", (byte) 20);
        userService.saveUser("Николай", "Николаев", (byte) 25);
        userService.saveUser("Сергей", "Орлов", (byte) 32);
        userService.removeUserById(4);
        System.out.println(userService.getAllUsers());
        userService.cleanUsersTable();
        userService.dropUsersTable();

    }
}
