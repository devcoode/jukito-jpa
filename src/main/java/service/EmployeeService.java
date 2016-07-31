package service;

import com.google.inject.persist.Transactional;
import entity.Employee;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

public class EmployeeService {

    @Inject
    private EntityManager em;

    @Transactional
    public Long save(final String name) {
        final Employee employee = new Employee(name);
        em.persist(employee);
        em.flush();
        return employee.getId();
    }

    public List<String> findAllNames() {
        return em.createQuery("SELECT e.name FROM Employee e ORDER BY e.name", String.class).getResultList();
    }
}
