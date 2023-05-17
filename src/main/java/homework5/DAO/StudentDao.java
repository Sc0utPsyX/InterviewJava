package homework5.DAO;

import homework5.HibernateUtil;
import homework5.model.Student;
import org.hibernate.Session;

import java.util.List;
import java.util.Objects;

public class StudentDao {
    public List<Student> getAll(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        List<Student> students = session.createQuery("select s from Student s", Student.class).getResultList();
        session.getTransaction().commit();
        session.close();
        return students;
    }

    public Student getById(Long id){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Student student = session.get(Student.class, id);
        session.getTransaction().commit();
        session.close();
        return student;
    }

    public void deleteById(Long id){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.remove(getById(id));
        session.getTransaction().commit();
        session.close();
    }
    public Student saveOrUpdate(Student student) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        if (Objects.isNull(student.getId())) {
            session.persist(student);
        } else {
            session.merge(student);
        }
        session.getTransaction().commit();
        session.close();
        return student;
    }
}
