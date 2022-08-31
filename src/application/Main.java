package application;

import java.util.List;

import model.dao.DAOFactory;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

public class Main {
	
	public static void main(String[] args) {
		SellerDAO sellerDAO = DAOFactory.createSellerDAO();
		
		System.out.println("--- TEST 1: Seller findById ---");
		Seller seller = sellerDAO.findById(3);
		System.out.println(seller);
		
		breakLine();
		
		System.out.println("--- TEST 2: Seller findByDepartment ---");
		Department department = new Department(2, null);
		List<Seller> list = sellerDAO.findByDepartment(department);
		for (Seller obj : list)
			System.out.println(obj);
		
		breakLine();
	}
	
	public static void breakLine() {
		System.out.println();
	}

}