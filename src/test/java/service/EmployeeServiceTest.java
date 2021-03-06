package service;

import com.google.inject.persist.Transactional;
import entity.Employee;
import helper.DatabaseModule;
import helper.JpaJukitoRunner;
import org.jukito.UseModules;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(JpaJukitoRunner.class)
@UseModules(DatabaseModule.class)
public class EmployeeServiceTest {

    @Inject
    private EmployeeService sut;
    @Inject
    private EntityManager em;

    @Before
    @Transactional
    public void setUp() throws Exception {
        em.createQuery("DELETE FROM Employee").executeUpdate();
    }

    @Test
    public void saveShouldPersistJohnDoe() throws Exception {
        final String name = "John Doe";

        final long id = sut.save(name);

        final Employee employee = em.find(Employee.class, id);
        assertThat(employee.getName(), is(name));
    }

    @Test
    public void findAllNamesShouldReturnExpectedResult() throws Exception {
        sut.save("Jane Doe");
        sut.save("John Doe");

        final List<String> result = sut.findAllNames();

        assertThat(result.size(), is(2));
    }
}
