package org.isdb.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.isdb.model.Product;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository {
	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert productInsert;

	public ProductRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;

		this.productInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("product_info")
				.usingGeneratedKeyColumns("id");
	}

	public int save(Product product) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("name", product.getName());
		parameters.put("price", product.getPrice());
		parameters.put("quantity", product.getQuantity());
		parameters.put("model", product.getModel());

		Number key = productInsert.executeAndReturnKey(parameters);
		return key.intValue();
	}

	public Optional<Product> findById(int id) {
		try {
			String sql = "SELECT * FROM product_info WHERE ID = ?";
			return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new ProductRowMapper(), id));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	public List<Product> findAll() {
		String sql = "SELECT * FROM product_info";
		return jdbcTemplate.query(sql, new ProductRowMapper());
	}

	public int update(Product product) {
		String sql = "UPDATE product_info SET name = ?,  price = ?,  quantity = ?, model = ? WHERE id = ?";

		return jdbcTemplate.update(sql, product.getName(), product.getPrice(), product.getQuantity(),
				product.getModel(), product.getId());
	}

	public int deleteById(int id) {
		String sql = "DELETE FROM product_info WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}

	public boolean existsById(int id) {
		String sql = "SELECT COUNT(*) FROM product_info WHERE id = ?";
		Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
		return count != null && count > 0;
	}

	public List<Product> findByName(String name) {

		String sql = "SELECT * FROM product_info WHERE name LIKE ?";
		return jdbcTemplate.query(sql, new ProductRowMapper(), "%" + name + "%");
	}

	private static class ProductRowMapper implements RowMapper<Product> {
		@Override
		public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Product(rs.getInt("id"), rs.getString("name"), rs.getDouble("price"), rs.getInt("quantity"),
					rs.getString("model"));
		}
	}

	public Product saveAndReturnProduct(Product product) {
		try (Connection connection = jdbcTemplate.getDataSource().getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(
						"INSERT INTO product_info (name,price, quantity, model) VALUES (?, ?, ?, ?)",
						Statement.RETURN_GENERATED_KEYS)) {

			// Set the parameters
			preparedStatement.setString(1, product.getName());
			preparedStatement.setDouble(2, product.getPrice());
			preparedStatement.setInt(3, product.getQuantity());
			preparedStatement.setString(4, product.getModel());

			// Execute the insert
			int affectedRows = preparedStatement.executeUpdate();

			if (affectedRows == 0) {
				throw new SQLException("Creating product failed, no rows affected.");
			}

			// Get the generated ID
			try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					int id = generatedKeys.getInt(1);

					// Set the ID in the product object
					Product savedProduct = new Product(id, product.getName(), product.getPrice(), product.getQuantity(),
							product.getModel());

					return savedProduct;
				} else {
					throw new SQLException("Creating product failed, no ID obtained.");
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error saving product", e);
		}
	}
}
