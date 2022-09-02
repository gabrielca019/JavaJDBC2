package model.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import model.dao.DepartmentDAO;
import model.entities.Department;

public class DepartmentDAOImplementation implements DepartmentDAO {

	private Connection conn;
	
	public DepartmentDAOImplementation(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Department department) {
		
	}

	@Override
	public void update(Department department) {
		
	}

	@Override
	public void deleteById(Integer id) {
		
	}

	@Override
	public Department findById(Integer id) {
		return null;
	}

	@Override
	public List<Department> findAll() {
		return null;
	}

}
