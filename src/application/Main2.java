package application;

import model.dao.DAOFactory;
import model.dao.DepartmentDAO;
import model.entities.Department;

public class Main2 {

	public static void main(String[] args) {
		DepartmentDAO departmentDAO = DAOFactory.createDepartmentDAO();
		
		System.out.println("--- TEST 1: Department insert ---");
		Department newDepartment = new Department(null, "Adventure");
		departmentDAO.insert(newDepartment);
		System.out.println(newDepartment);
	}

}
