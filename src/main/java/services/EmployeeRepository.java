package services;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {
    List<Employee> findByDepartId(Integer depart_id);

    @Query("SELECT AVG(one.salary) from Employee one")
    int getAverageSalary();

    @Query("SELECT one.id from Employee one")
    Integer[] getTest_id();

    @Query("SELECT one.fname from Employee one")
    String[] getTest_fname();
}
