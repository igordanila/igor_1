package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.management.Query;
import javax.persistence.EntityManager;
import java.util.Iterator;
import java.util.List;

@Controller
public class TestController {

    public static final String REQUEST_MAPPED_CLASS_VIEW_NAME = "igor";
    public static final String REQUEST_MAPPED_CLASS_VIEW_NAME_ERR = "igor_err";

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartsRepository departsRepository;


    @RequestMapping(value = "/add_employee", method = RequestMethod.POST)
    public  String add_employee      (Model model,@RequestParam String fname,
                                @RequestParam String lname,
                                @RequestParam Double salary,
                                @RequestParam Integer depart_id) {

        if(departsRepository.exists(depart_id)) {
            Employee employee = new Employee();
            employee.setFname(fname);
            employee.setLname(lname);
            employee.setSalary(salary);
            employee.setDepart(departsRepository.findOne(depart_id));
            employeeRepository.save(employee);

            model.addAttribute("fname", fname);
            model.addAttribute("lname", lname);
            model.addAttribute("salary", salary);
            model.addAttribute("depart_id", depart_id);

            return REQUEST_MAPPED_CLASS_VIEW_NAME;
        }
        model.addAttribute("depart_id", depart_id);
        return REQUEST_MAPPED_CLASS_VIEW_NAME_ERR;

     }


    @RequestMapping(value = "/delete_employee", method = RequestMethod.POST)
    public  String delete_employees (Model model,@RequestParam Integer id) {
        if(employeeRepository.exists(id)) {
            employeeRepository.delete(id);
            model.addAttribute("delete_id", id);
            return "igor_delete";
        }
        model.addAttribute("depart_id", id);
        return REQUEST_MAPPED_CLASS_VIEW_NAME_ERR;
    }





    @RequestMapping("/employees")
    public @ResponseBody Iterable<Employee> getAllUsers() {

        Iterable<Employee> allEmployee = employeeRepository.findAll();
        Iterator<Employee> employeeIterator = allEmployee.iterator();
        while (employeeIterator.hasNext()) {
            Employee currentDepartment = employeeIterator.next();
            System.out.println(currentDepartment.getId());
            System.out.println(currentDepartment.getFname());
            System.out.println(currentDepartment.getLname());
            System.out.println(currentDepartment.getSalary());
            System.out.println("departament = "+currentDepartment.getDepart());

        }


        return employeeRepository.findAll();
    }


    @RequestMapping("/departs")
    public @ResponseBody Iterable<Depart> getAllDeparts() {


        Iterable<Depart> allDeparts = departsRepository.findAll();
/*
        for (Depart currentDepartment: allDeparts) {
            System.out.println(currentDepartment.getName());
        }
*/
        Iterator<Depart> departsIterator = allDeparts.iterator();
        while (departsIterator.hasNext()) {
            Depart currentDepartment = departsIterator.next();
            System.out.println(currentDepartment.getName());
            System.out.println(currentDepartment.getId());

        }

        return departsRepository.findAll();
    }


    @RequestMapping(path = "/average_salary")
    public @ResponseBody int getAverageSalary() {

        Integer test_id[] = employeeRepository.getTest_id();
        for(int k = 0; k < test_id.length; k++)
            System.out.println(test_id[k]);

        String test_fname[] = employeeRepository.getTest_fname();
        for(int k = 0; k < test_fname.length; k++)
            System.out.println(test_fname[k]);



        int sum = employeeRepository.getAverageSalary();
        return sum;
    }



    @PostMapping(path="/departs/add")
    public @ResponseBody String addNewDepart (@RequestParam String name) {
        Depart department = new Depart();
        department.setName(name);
        departsRepository.save(department);
        return "Saved";
    }

    @PostMapping(path="/employees/add")
    public @ResponseBody String addNewEmployee (@RequestParam String fname,
                                                @RequestParam String lname,
                                                @RequestParam Double salary,
                                                @RequestParam Integer depart_id) {
        if(departsRepository.exists(depart_id)) {
            Employee employee = new Employee();
            employee.setFname(fname);
            employee.setLname(lname);
            employee.setSalary(salary);
            employee.setDepart(departsRepository.findOne(depart_id));
            employeeRepository.save(employee);
            return "Saved";
        }
        return "Failed";
    }

    @DeleteMapping(path="/employees/delete")
    public @ResponseBody String deleteEmployee (@RequestParam Integer id) {
        if(employeeRepository.exists(id)) {
            employeeRepository.delete(id);
            return "Deleted";
        }
        return "Failed";
    }

    @DeleteMapping(path="/departs/delete")
    public @ResponseBody String deleteDepart(@RequestParam Integer id) {
        if(departsRepository.exists(id) && employeeRepository.findByDepartId(id).isEmpty()) {
            departsRepository.delete(id);
            return "Deleted";
        }
        return "Failed";
    }
}
