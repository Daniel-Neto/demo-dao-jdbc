package application;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
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
		
		List<Seller> sellers2 = sellerDao.findAll();
		System.out.println("=== Teste 3: seller findAll()");
		for(Seller se: sellers2) {
			System.out.println(se);
		}
		
		System.out.println("==== Teste 4: seller insert");
		Department dep = new Department(1, null);
		Seller newSeller = new Seller(null, "Daniel", "daniel@daniel.com", new Date(), 3500.0, dep);
		sellerDao.insert(newSeller);
		System.out.println("New seller id: " + newSeller.getId());
		System.out.println("==== Teste 5: seller update");
		seller = sellerDao.findById(1);
		seller.setName("Martha Waine");
		sellerDao.update(seller);
		System.out.println("Update completed");
		System.out.println("==== Teste 6: seller delete");
		System.out.println("Enter id for delete test: ");
		int id = sc.nextInt();
		sellerDao.deleteById(id);
		System.out.println("Delete completed");
		sc.close();
		
	}

}
