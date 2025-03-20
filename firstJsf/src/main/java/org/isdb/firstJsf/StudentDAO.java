package org.isdb.firstJsf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

	public void create(Student student) throws Exception {

		String sql = "INSERT INTO STUDENTJSF (name,email,course) VALUES (?,?,?)";
		try (Connection connection = DatabaseConfig.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			preparedStatement.setString(1, student.getName());

			preparedStatement.setString(2, student.getEmail());
			preparedStatement.setString(3, student.getCourse());
			preparedStatement.executeUpdate();

		}
	}

	public List<Student> readAll() throws Exception {
		List<Student> students = new ArrayList<>();
		String sql = "SELECT * FROM STUDENTJSF ORDER BY id";
		try (Connection connection = DatabaseConfig.getConnection();
				Statement stmt = connection.createStatement();
				ResultSet resultSet = stmt.executeQuery(sql)) {

			while (resultSet.next()) {
				Student student = new Student(resultSet.getLong("id"), resultSet.getString("name"),
						resultSet.getString("email"), resultSet.getString("course"));
				students.add(student);

			}
		}
		return students;
	}

	public void update(Student student) throws Exception {
		String sql = "UPDATE STUDENTJSF SET name = ?, email =?, course=? WHERE id =?";

		try (Connection connection = DatabaseConfig.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, student.getName());
			stmt.setString(2, student.getEmail());
			stmt.setString(3, student.getCourse());
			stmt.setLong(4, student.getId());

			stmt.executeUpdate();

		}
	}

	public void delete(long id) throws Exception {
		String sql = "DELETE FROM STUDENTJSF WHERE id =?";
		try (Connection connection = DatabaseConfig.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setLong(1, id);
			preparedStatement.executeUpdate();

		}
	}

}
