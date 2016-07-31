package service;

import com.google.inject.persist.Transactional;
import com.google.inject.persist.UnitOfWork;
import entity.Employee;
import helper.DatabaseModule;
import helper.JpaJukitoRunner;
import org.jukito.UseModules;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(JpaJukitoRunner.class)
@UseModules(DatabaseModule.class)
public class EmployeeServiceManagesUOWTest {

    @Inject
    private Provider<EmployeeService> sut;
    @Inject
    private Provider<EntityManager> em;
    @Inject
    private UnitOfWork unitOfWork;

    @Before
    @Transactional
    public void setUp() throws Exception {
        em.get().createQuery("DELETE FROM Employee").executeUpdate();
    }

    @Test
    public void saveShouldPersistJohnDoe() throws Exception {
        final String name = "John Doe";
        newEntityManager();

        final long id = sut.get().save(name);

        newEntityManager();
        final Employee employee = em.get().find(Employee.class, id);
        assertThat(employee.getName(), is(name));
    }

    private void newEntityManager() {
        unitOfWork.end();
        unitOfWork.begin();
    }
}
