package application;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		
		SellerDao sellerDao = DaoFactory.createSellerDao(); // instancia a implementação de sellerDao usando
														    // a instância SellerDaoJDBC. Desse modo, vai pegar
															// as operações implementadas em SellerDaoJDBC.
		// 
		//Desse modo o meu programa principal não conhece a implementação da classe SellerDaoJDBC.
		System.out.println("==== Teste 1: seller findById ====");
		Seller seller = sellerDao.findById(3);
		System.out.println(seller);
		System.out.println("==== Teste 2: seller findByDepartment ====");
		Department department = new Department(2, null);
		List<Seller> sellers = sellerDao.findByDepartment(department);
		
		for(Seller sel: sellers) {
			System.out.println(sel);
		}
		
	}

}
