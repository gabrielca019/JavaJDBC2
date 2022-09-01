package model.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	public void insert(Seller obj) {
		
	}

	@Override
	public void update(Seller obj) {
		
	}

	@Override
	public void deleteById(Integer id) {
		
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