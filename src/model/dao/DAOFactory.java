package model.dao;

import model.dao.implementation.SellerDAOImplementation;

public class DAOFactory {
	
	public static SellerDAO createSellerDAO() {
		return new SellerDAOImplementation();
	}

}
