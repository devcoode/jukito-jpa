package service;

import com.google.inject.persist.Transactional;
import entity.Employee;
import entity.EmployeeName;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.logging.Logger;

public class EmployeeService {

    private static final Logger log = Logger.getLogger(EmployeeService.class.getName());

    @Inject
    private EntityManager em;

    @Transactional
    public Long save(final String firstName, final String lastName) {
        final Employee employee = new Employee(new EmployeeName(firstName, lastName));
        em.persist(employee);
        em.flush();
        return employee.getId();
    }

    public List<EmployeeName> findAllNames(){
        return em.createQuery("SELECT e FROM Employee e ORDER BY e.employeeName.firstName", EmployeeName.class).getResultList();
    }
}
