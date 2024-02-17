package application;

import java.util.Date;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		Department obj = new Department(1,"Books");
		System.out.println(obj);
		Seller seller = new Seller(21, "Bob", "bob@gmail.com", new Date(), 3000.0, obj);
		System.out.println(seller);
		
		SellerDao sellerDao = DaoFactory.createSellerDao(); // instancia a implementação de sellerDao usando
														    // a instância SellerDaoJDBC. Desse modo, vai pegar
															// as operações implementadas em SellerDaoJDBC.
		// 
		//Desse modo o meu programa principal não conhece a implementação da classe SellerDaoJDBC.
	}

}
