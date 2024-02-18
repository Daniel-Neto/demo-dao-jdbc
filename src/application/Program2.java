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
	}

}
