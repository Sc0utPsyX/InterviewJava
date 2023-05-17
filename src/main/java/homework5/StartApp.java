package homework5;

import com.github.javafaker.Faker;
import homework5.DAO.StudentDao;
import homework5.model.Student;

public class StartApp {
    public static void main(String[] args) {
        StudentDao studentDao = new StudentDao();
        Faker faker = new Faker();
        for (int i = 0; i < 1000; i++) {
            studentDao.saveOrUpdate(new Student(faker.name().fullName(), faker.number().numberBetween(1, 5)));
        }
        studentDao.deleteById(999L);
        studentDao.getAll().forEach(System.out::println);
        Student student = studentDao.getById(500L);
        System.out.println(studentDao.getById(500L));
        student.setName("New Name");
        studentDao.saveOrUpdate(student);
        System.out.println(studentDao.getById(500L));
    }
}
