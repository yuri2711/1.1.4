package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService service = new UserServiceImpl();
        service.createUsersTable();
        //service.createUsersTable();
        //service.dropUsersTable();
        service.saveUser("Юрий", "Тарасенко", (byte) 35);
        service.saveUser("Иван", "Ирзин", (byte) 16);
        service.saveUser("Сергей", "Облаухов", (byte) 35);
        service.saveUser("Антон", "Царев", (byte) 29);
        service.saveUser("Юрий", "Герасимов", (byte) 65);
//        service.removeUserById(2);
       for (User user : service.getAllUsers()) System.out.println(user);
    }
}
