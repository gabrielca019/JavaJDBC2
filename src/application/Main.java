package application;

import model.dao.DAOFactory;
import model.dao.SellerDAO;
import model.entities.Seller;

public class Main {
	
	public static void main(String[] args) {
		SellerDAO sellerDAO = DAOFactory.createSellerDAO();
		
		System.out.println("--- TEST 1: Seller findById ---");
		Seller seller = sellerDAO.findById(3);
		System.out.println(seller);
	}

}
