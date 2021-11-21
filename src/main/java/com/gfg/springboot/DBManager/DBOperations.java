package com.gfg.springboot.DBManager;

import java.sql.*;
import java.util.*;

import org.springframework.stereotype.Component;

import com.gfg.springboot.model.Person;
import com.gfg.springboot.request.CreateRequest;

public class DBOperations {

	private static volatile Connection connection;

	public static Connection getConnection() throws SQLException {

		System.out.println("connection created");
		connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/person_4", "root", "Somu@1997");
		System.out.println("connection created");

		return connection;
	}

	public static void closeConnection() throws SQLException {
		if (connection != null) {
			synchronized (DBOperations.class) {
				if (connection != null) {
					connection.close();
				}
			}
		}

	}

	public static Person getPersonById(int id) {
		return null;
	}

	public static List<Person> getPerson() throws SQLException {

		List<Person> pList = new ArrayList<Person>();
		connection = getConnection();
		PreparedStatement ps = connection.prepareStatement("select * from person");
		ResultSet resultSet = ps.executeQuery();
		while (resultSet.next()) {
			String name = resultSet.getString(2);
			String address = resultSet.getString(3);
			int age = resultSet.getInt(4);
			int id = resultSet.getInt(1);
			Person person = new Person();
			person.setAddress(address);
			person.setAge(age);
			person.setName(name);
			person.setId(id);
			pList.add(person);

		}
		return pList;
	}

	public static String deletePerson(int id) throws SQLException {

		connection = getConnection();
		PreparedStatement prepareStatement = connection.prepareStatement("DELETE FROM person where id=?");
		prepareStatement.setInt(1, id);
		int ans = prepareStatement.executeUpdate();
		if (ans > 0) {
			return "Person with id " + id + " Deleted Succesfully";
		} else {
			return "Person with given ID is not Found";
		}

	}

	public static String updatePerson(int id, Person person) throws SQLException {

		String selectString = "select * from person where id=?";
		connection = getConnection();
		PreparedStatement prepareStatement = connection.prepareStatement(selectString);

		ResultSet resultSet = prepareStatement.executeQuery();
		Person p = null;
		while (resultSet.next()) {
			p = new Person();
			p.setId(resultSet.getInt(1));
			p.setName(resultSet.getString(2));
			p.setAddress(resultSet.getString(3));
			p.setId(resultSet.getInt(4));

		}
		if (p == null) {
			return "Person with that specific ID " + id + " is not Found";
		}

		String updatedString = "UPDATE Person SET name=?,address=?,age=? WHERE id=?";

		prepareStatement = connection.prepareStatement(updatedString);
		if (person.getName() != null) {
			prepareStatement.setString(1, person.getName());
		} else {
			prepareStatement.setString(1, p.getName());
		}
		if (person.getAddress() != null) {
			prepareStatement.setString(2, person.getAddress());
		} else {
			prepareStatement.setString(2, p.getAddress());
		}
		if (person.getAge() != 0) {
			prepareStatement.setInt(3, person.getAge());
		} else {
			prepareStatement.setInt(3, p.getAge());
		}
		prepareStatement.setInt(4, id);

		return "Object updated successFully ";
	}

	public static void createTable(String name) throws SQLException {
		connection = getConnection();
		Statement statement = connection.createStatement();
		System.out.println(name);
		String query = "create table " + name
				+ " (id int PRIMARY KEY AUTO_INCREMENT, name VARCHAR(20), address varchar(50),age int)";
		boolean executed = statement.execute(query);
		if (executed) {
			System.out.println("table " + name + " created");
		}
		closeConnection();
	}

	public static void insertPerson(CreateRequest createRequest) throws SQLException {
		connection = getConnection();
		String name = createRequest.getName();
		int age = createRequest.getAge();
		String address = createRequest.getAddress();
		PreparedStatement prepareStatement = connection.prepareStatement("insert into person values(null,?,?,?)");
		prepareStatement.setString(1, name);
		prepareStatement.setString(2, address);
		prepareStatement.setInt(3, age);
		int row_affected = prepareStatement.executeUpdate();
		if (row_affected > 0) {
			System.out.println("Inserted Successfully ");
		} else {
			System.out.println("Not able to insert ");
		}
	}

}
