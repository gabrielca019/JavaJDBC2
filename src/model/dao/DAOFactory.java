package model.dao;

import db.DB;
import model.dao.implementation.DepartmentDAOImplementation;
import model.dao.implementation.SellerDAOImplementation;

public class DAOFactory {
	
	public static SellerDAO createSellerDAO() {
		return new SellerDAOImplementation(DB.getConnection());
	}
	
	public static DepartmentDAO createDepartmentDAO() {
		return new DepartmentDAOImplementation(DB.getConnection());
	}

}
