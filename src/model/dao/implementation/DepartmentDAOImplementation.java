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
			} else {
				throw new DbException("Unexpected error! no rows affected!");
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
		PreparedStatement preparedStatement = null;
		
		try {
			preparedStatement = conn.prepareStatement("UPDATE department "
													+ "SET "
													+ "Name = ? "
													+ "WHERE Id = ?");
			preparedStatement.setString(1, department.getName());
			preparedStatement.setInt(2, department.getId());
			preparedStatement.executeUpdate();
		} catch(SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(preparedStatement);
		}
	}

	@Override
	public void deleteById(Integer id) {
		/*PreparedStatement preparedStatement = null;
		
		try {
			preparedStatement = conn.prepareStatement("");
		} catch(SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(preparedStatement);
		}*/
	}

	@Override
	public Department findById(Integer id) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			preparedStatement = conn.prepareStatement("SELECT * "
													+ "FROM department "
													+ "WHERE Id = ?");
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				Department department = new Department();
				department.setId(resultSet.getInt("Id"));
				department.setName(resultSet.getString("Name"));
				return department;
			} else {
				throw new DbException("Department Id not exists!");
			}
		} catch(SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(resultSet);
			DB.closeStatement(preparedStatement);
		}
	}

	@Override
	public List<Department> findAll() {
		return null;
	}

}
