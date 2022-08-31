package model.dao;

import db.DB;
import model.dao.implementation.SellerDAOImplementation;

public class DAOFactory {
	
	public static SellerDAO createSellerDAO() {
		return new SellerDAOImplementation(DB.getConnection());
	}

}
