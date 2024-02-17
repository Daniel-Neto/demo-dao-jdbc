package model.dao;

import model.dao.impl.SellerDaoJDBC;

public class DaoFactory {
	
	// classe auxiliar responsável por instanciar os DAOs
	
	public static SellerDao createSellerDao() {
		return new SellerDaoJDBC();
	}
	
	
}
