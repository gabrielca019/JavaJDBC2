package application;

import java.util.Date;

import model.entities.Department;
import model.entities.Seller;

public class Main {
	
	public static void main(String[] args) {
		Department dp = new Department(1, "Kleber");
		Seller sl = new Seller(21, "Pereira", "pereir@gmail.com", new Date(), 3000.0, dp);
		System.out.println(sl);
	}

}
