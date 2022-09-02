package application;

import java.util.Date;
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
		
		System.out.println("--- TEST 3: Seller findAll ---");
		list = sellerDAO.findAll();
		for (Seller obj : list)
			System.out.println(obj);
		
		breakLine();
		
		System.out.println("--- TEST 4: Seller insert ---");
		Seller newSeller = new Seller(null, "Greg", "greg@gmail", new Date(), 4000.0, department);
		sellerDAO.insert(newSeller);
		System.out.println("Inserted! New id = " + newSeller.getId());

		breakLine();
		
		System.out.println("--- TEST 5: Seller update ---");
		seller = sellerDAO.findById(1);
		seller.setName("Calango Tango");
		sellerDAO.update(seller);
		System.out.println("Update completed!");
		
		breakLine();
		
		System.out.println("--- TEST 6: Seller deleteById ---");
		sellerDAO.deleteById(6);
		System.out.println("Delete completed!");
	}
	
	public static void breakLine() {
		System.out.println();
	}

}