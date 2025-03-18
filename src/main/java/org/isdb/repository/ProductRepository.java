package org.isdb.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
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
		this.productInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("product_sb")
				.usingGeneratedKeyColumns("id");
	}

	private static class ProductRowMapper implements RowMapper<Product> {
		@Override
		public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Product(rs.getLong("id"), rs.getString("name"), rs.getDouble("price"), rs.getInt("quantity"),
					rs.getObject("buyDate", LocalDate.class), rs.getObject("sellDate", LocalDate.class),
					rs.getDouble("amount"));
		}
	}

	public int savedAll(Product product) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("name", product.getName());
		parameters.put("price", product.getPrice());
		parameters.put("quantity", product.getQuantity());
		parameters.put("buyDate", product.getBuyDate());
		parameters.put("sellDate", product.getSellDate());
		parameters.put("amount", product.getAmount());

		Number key = productInsert.executeAndReturnKey(parameters);
		return key.intValue();
	}

	public Optional<Product> findById(long id) {

		try {
			String sql = "SELECT * FROM product_sb WHERE ID = ?";
			return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new ProductRowMapper(), id));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

}
