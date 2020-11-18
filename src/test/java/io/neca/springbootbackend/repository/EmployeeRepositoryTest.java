package io.neca.springbootbackend.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import io.neca.springbootbackend.model.Employee;
import org.junit.jupiter.api.DisplayName;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;

@DataJpaTest
@ActiveProfiles("test")
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository repo;
    @Autowired
    private JdbcTemplate jdbc;
    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("Injection Test")
    void injectionTest() {
        assertThat(repo).isNotNull();
        assertThat(jdbc).isNotNull();
        assertThat(entityManager).isNotNull();
    }

    // 4 employees in data.sql
    @Test
    @DisplayName("Should find all employees")
    void shouldFindAllEmployees() {
        Iterable<Employee> employees = this.repo.findAll();
        assertThat(employees).hasSize(4);
    }

    // 4 employees in data.sql
    @Test
    @DisplayName("Should find all employees v2")
    void shouldFindAllEmployeesV2() {
        assertThat(this.jdbc.queryForObject("SELECT COUNT(*) FROM EMPLOYEE", Integer.class)).isEqualTo(4);
    }

    @Test
    @DisplayName("Should store an employee")
    void shouldStoreAnEmployee() {
        Employee emp = this.repo.save(new Employee("Donald", "Trump", "trump123@gmail.com"));

        assertThat(emp).hasFieldOrPropertyWithValue("firstName", "Donald");
        assertThat(emp).hasFieldOrPropertyWithValue("lastName", "Trump");
        assertThat(emp).hasFieldOrPropertyWithValue("email", "trump123@gmail.com");
    }

    @Test
    @DisplayName("Should find employee by id")
    void shouldFindEmployeeById() {
        Employee emp = new Employee("Auguste", "Comte", "comte123@gmail.com");
        this.entityManager.persist(emp);

        Employee emp1 = this.repo.findById(emp.getId()).get();

        assertThat(emp1).isEqualTo(emp);
    }

    @Test
    @DisplayName("Should update employee by id")
    void shouldUpdateEmployeeById() {
        Employee emp = new Employee("Mark", "Smith", "mark123@gmail.com");
        this.entityManager.persist(emp);

        Employee updatedEmp = new Employee("UpdatedMark", "UpdatedSmith", "update123@gmail.com");

        Employee emp1 = this.repo.findById(emp.getId()).get();
        emp1.setFirstName(updatedEmp.getFirstName());
        emp1.setLastName(updatedEmp.getLastName());
        emp1.setEmail(updatedEmp.getEmail());
        this.repo.save(emp1);

        Employee checkEmp = this.repo.findById(emp.getId()).get();

        assertThat(checkEmp.getId()).isEqualTo(emp.getId());
        assertThat(checkEmp.getFirstName()).isEqualTo(updatedEmp.getFirstName());
        assertThat(checkEmp.getLastName()).isEqualTo(updatedEmp.getLastName());
        assertThat(checkEmp.getEmail()).isEqualTo(updatedEmp.getEmail());
    }

    @Test
    @DisplayName("Should delete employee by id")
    void shouldDeleteEmployeeById() {
        Employee emp = new Employee("Michael", "Jackson", "mick123@gmail.com");
        this.entityManager.persist(emp);

        // size 5
        int size = this.repo.findAll().size();

        // size--
        this.repo.deleteById(emp.getId());

        Iterable<Employee> employees = this.repo.findAll();

        assertThat(employees).hasSize(size - 1); // 5 - 1
    }

}