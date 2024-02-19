package application;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Program2 {

	public static void main(String[] args) {
		
		DepartmentDao depDao = DaoFactory.createDepartmentDao();
		System.out.println("==== Teste 1: Find Department by Id");
		Department dep = depDao.findById(1);
		System.out.println(dep);
		System.out.println("=== Teste 2: Find All");
		List<Department> deps = depDao.findAll();
		for(Department d: deps) {
			System.out.println(d);
		}
		System.out.println("Teste 3: Insert Department");
		Department dep2 = new Department(null,"Used Books");
		depDao.insert(dep2);
		System.out.println(dep2);
		System.out.println("Teste 4: Update Department");
		dep2.setId(5);
		dep2.setName("New Wood");
		depDao.update(dep2);
		System.out.println("Update completed");
		System.out.println("Teste 5: Delete Department");
		depDao.deleteById(4);
		System.out.println("Department deleted.");
	}

}
