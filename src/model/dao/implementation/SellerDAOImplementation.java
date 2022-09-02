package model.dao.implementation;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

public class SellerDAOImplementation implements SellerDAO {
	
	private Connection conn;
	
	public SellerDAOImplementation(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller seller) {
		PreparedStatement preparedStatment = null;
		ResultSet resultSet = null;
		
		try {
			preparedStatment = conn.prepareStatement("INSERT INTO seller "
												   + "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
												   + "VALUES "
												   + "(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			preparedStatment.setString(1, seller.getName());
			preparedStatment.setString(2, seller.getEmail());
			preparedStatment.setDate(3, new Date(seller.getBirthDate().getTime()));
			preparedStatment.setDouble(4, seller.getBaseSalary());
			preparedStatment.setInt(5, seller.getDepartment().getId());
			
			int rowsAffected = preparedStatment.executeUpdate();
			
			if(rowsAffected > 0) {
				resultSet = preparedStatment.getGeneratedKeys();
				if(resultSet.next())
					seller.setId(resultSet.getInt(1));
				DB.closeResultSet(resultSet);
			} else {
				throw new DbException("Unexpected error! no rows affected!");
			}
		} catch(SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(preparedStatment);
		}
	}

	@Override
	public void update(Seller seller) {
		PreparedStatement preparedStatement = null;
		
		try {
			preparedStatement = conn.prepareStatement("UPDATE seller "
													+ "SET " 
													+ "Name = ?, "
													+ "Email = ?, "
													+ "BirthDate = ?, "
													+ "BaseSalary = ?, "
													+ "DepartmentId = ? "
													+ "WHERE Id = ?");
			preparedStatement.setString(1, seller.getName());
			preparedStatement.setString(2, seller.getEmail());
			preparedStatement.setDate(3, new Date(seller.getBirthDate().getTime()));
			preparedStatement.setDouble(4, seller.getBaseSalary());
			preparedStatement.setInt(5, seller.getDepartment().getId());
			preparedStatement.setInt(6, seller.getId());
			
			preparedStatement.executeUpdate();
		} catch(SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(preparedStatement);
		}
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement preparedStatement = null;
		
		try {
			preparedStatement = conn.prepareStatement("DELETE FROM seller "
													+ "WHERE Id = ?");
			preparedStatement.setInt(1, id);
			int rowsAffected = preparedStatement.executeUpdate();
			
			if(rowsAffected == 0) {
				throw new DbException("Seller id not exists");
			}
		} catch(SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(preparedStatement);
		}
	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			preparedStatement = conn.prepareStatement("SELECT seller.*,department.Name as DepName "
													+ "FROM seller INNER JOIN department "
													+ "ON seller.DepartmentId = department.Id "
													+ "WHERE seller.Id = ?");
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				Department department = instantiateDepartment(resultSet);
				Seller seller = instantiateSeller(resultSet, department);
				return seller;
			}
			return null;
		} catch(SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(preparedStatement);
			DB.closeResultSet(resultSet);
		}
	}

	@Override
	public List<Seller> findAll() {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Seller> list = new ArrayList<>();
		Map<Integer, Department> map = new HashMap<>();
		
		try {
			preparedStatement = conn.prepareStatement("SELECT seller.*,department.Name as DepName "
													+ "FROM seller INNER JOIN department "
													+ "ON seller.DepartmentId = department.Id "
													+ "ORDER BY Name");
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				Department dep = map.get(resultSet.getInt("DepartmentId")); //instancia um departamento verificando se já existe um com o id retornado
				if(dep == null) { //se não existir, ele vai criar, e assim nos proximos passos do while ele já estara salvo, para que todos objetos do tipo seller apontem para o mesmo objeto de department
					dep = instantiateDepartment(resultSet);
					map.put(resultSet.getInt("DepartmentId"), dep);
				}
				
				Seller seller = instantiateSeller(resultSet, dep);
				list.add(seller);
			}
			return list;
		} catch(SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(preparedStatement);
			DB.closeResultSet(resultSet);
		}
	}
	
	@Override
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Seller> list = new ArrayList<>();
		Map<Integer, Department> map = new HashMap<>();
		
		try {
			preparedStatement = conn.prepareStatement("SELECT seller.*,department.Name as DepName "
													+ "FROM seller INNER JOIN department "
													+ "ON seller.DepartmentId = department.Id "
													+ "WHERE department.Id = ? "
													+ "ORDER BY Name");
			preparedStatement.setInt(1, department.getId());
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				Department dep = map.get(resultSet.getInt("DepartmentId")); //instancia um departamento verificando se já existe um com o id retornado
				if(dep == null) { //se não existir, ele vai criar, e assim nos proximos passos do while ele já estara salvo, para que todos objetos do tipo seller apontem para o mesmo objeto de department
					dep = instantiateDepartment(resultSet);
					map.put(resultSet.getInt("DepartmentId"), dep);
				}
				
				Seller seller = instantiateSeller(resultSet, dep);
				list.add(seller);
			}
			return list;
		} catch(SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(preparedStatement);
			DB.closeResultSet(resultSet);
		}
	}
	
	private Department instantiateDepartment(ResultSet resultSet) throws SQLException {
		Department department = new Department();
		department.setId(resultSet.getInt("DepartmentId"));
		department.setName(resultSet.getString("DepName"));
		return department;
	}
	
	private Seller instantiateSeller(ResultSet resultSet, Department department) throws SQLException {
		Seller seller = new Seller();
		seller.setId(resultSet.getInt("Id"));
		seller.setName(resultSet.getString("Name"));
		seller.setEmail(resultSet.getString("Email"));
		seller.setBirthDate(resultSet.getDate("BirthDate"));
		seller.setBaseSalary(resultSet.getDouble("BaseSalary"));
		seller.setDepartment(department);
		return seller;
	}

	

}