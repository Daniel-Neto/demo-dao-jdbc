package model.dao.impl;

import java.sql.Connection;
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
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {

	private Connection conn;

	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller obj) {
		
		PreparedStatement st = null;

		try {
			st = conn.prepareStatement(
					"INSERT INTO seller (Name, Email, BirthDate, BaseSalary, DepartmentId) VALUES " + "(?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartment().getId());

			int rowsAffected = st.executeUpdate();
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys(); // generatedKeys retorna um ResultSet
				while (rs.next()) {
					int id = rs.getInt(1); // 1 indica o valor da primeira coluna, que é o ID
					obj.setId(id);
				}
				DB.closeResultSet(rs);
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			
		}

	}

	@Override
	public void update(Seller obj) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(
					"UPDATE seller SET Name = ?,  Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? " 
					+ "WHERE Id = ?",
					Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartment().getId());
			st.setInt(6, obj.getId());
	
			st.executeUpdate();
	
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			
		}

	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("SELECT seller.*, department.Name as DepName FROM seller INNER JOIN department"
					+ " ON seller.departmentId = department.Id WHERE seller.Id = ?");

			st.setInt(1, id);
			rs = st.executeQuery();
			// testa se veio algum resultado na consulta
			if (rs.next()) {
				// Pega os dados do banco de dados e cria um objeto departamento
				Department dep = instantiateDepartment(rs);
				Seller obj = instantiateSeller(rs, dep);
				return obj;
			}

			return null;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
		Seller obj = new Seller();
		obj.setId(rs.getInt("Id"));
		obj.setName(rs.getString("Name"));
		obj.setEmail(rs.getString("Email"));
		obj.setBaseSalary(rs.getDouble("BaseSalary"));
		obj.setBirthDate(rs.getDate("BirthDate"));
		obj.setDepartment(dep);
		return obj;
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId")); // obtém o Id do Departamento//
		dep.setName(rs.getString("DepName"));
		return dep;
	}

	@Override
	public List<Seller> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		List<Seller> sellers = new ArrayList<>();

		try {
			st = conn.prepareStatement("SELECT seller.*, department.Name as DepName from seller "
					+ "INNER JOIN department ON seller.DepartmentId = department.Id");

			rs = st.executeQuery();
			Map<Integer, Department> map = new HashMap<>();

			while (rs.next()) {
				Department dep = map.get(rs.getInt("DepartmentId"));

				if (dep == null) {
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}

				Seller seller = instantiateSeller(rs, dep);
				sellers.add(seller);
			}

			return sellers;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

	@Override
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement st = null;
		ResultSet rs = null;
		List<Seller> sellers = new ArrayList<>();

		try {
			st = conn.prepareStatement("SELECT seller.*, department.Name as DepName FROM seller "
					+ "INNER JOIN department ON department.Id = seller.DepartmentId "
					+ "WHERE department.Id = ? ORDER BY Name");

			st.setInt(1, department.getId());
			rs = st.executeQuery();

			Map<Integer, Department> map = new HashMap<>();

			while (rs.next()) {

				// Essa verificação serve para instanciar apenas um objeto Departmento em
				// memória, verificando
				// se o Department já existe. Isso evita repetição de objetos.

				Department dep = map.get(rs.getInt("DepartmentId")); // Obtém o department se ele existir
				if (dep == null) {
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep); // se não existe, insere no map
				}

				Seller seller = instantiateSeller(rs, dep);
				sellers.add(seller);
			}

			return sellers;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

}
