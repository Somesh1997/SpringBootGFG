package com.gfg.springboot.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gfg.springboot.DBManager.DBOperations;
import com.gfg.springboot.model.Person;
import com.gfg.springboot.request.CreateRequest;

@RestController
public class GFGController {

	@GetMapping("/getAllPerson")
	public List<Person> getAllPersons() throws SQLException {
		return DBOperations.getPerson();
	}

	@PostMapping("/createTable/{name}")
	public void createTable(@PathVariable("name") String name) throws SQLException {
		System.out.println("hshhsh");
		DBOperations.createTable(name);
	}

	@PostMapping("/insertPerson")
	public void insertPerson(@RequestBody CreateRequest request) throws SQLException {
		DBOperations.insertPerson(request);
	}

	@DeleteMapping("/deletePerson/{id}")
	public String deletePerson(@PathVariable("id") int id) throws SQLException {
		return DBOperations.deletePerson(id);
	}

	@PutMapping("/updatePerson/{id}")
	public String updatePersonById(@PathVariable("id") int id, @RequestBody Person person) throws Exception {
		return DBOperations.updatePerson(id, person);
	}

}
