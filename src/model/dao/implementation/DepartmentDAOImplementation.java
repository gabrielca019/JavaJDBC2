package model.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartmentDAO;
import model.entities.Department;

public class DepartmentDAOImplementation implements DepartmentDAO {

	private Connection conn;
	
	public DepartmentDAOImplementation(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Department department) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			preparedStatement = conn.prepareStatement("INSERT INTO department " 
													+ "(Name) "
													+ "VALUES "
													+ "(?)", Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, department.getName());
			int rowsAffected = preparedStatement.executeUpdate();
			
			if(rowsAffected > 0) {
				resultSet = preparedStatement.getGeneratedKeys();
				if(resultSet.next()) {
					department.setId(resultSet.getInt(1));
				}
			}
		} catch(SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(preparedStatement);
			DB.closeResultSet(resultSet);
		}
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
