package com.Ex.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ex.model.Person;
import com.Ex.repository.PersonRepository;

@Service
public class PersonService {

	@Autowired
	PersonRepository personRepository;

	public Person findById(int id) {
		return personRepository.findById(id);
	}

	public synchronized Person save(Person person) {
		try {
			return personRepository.save(person);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	public List<Person> findAll() {
		return personRepository.findAll();
	}

	public void deleteById(int id) {
		// TODO Auto-generated method stub
		personRepository.deleteById(id);

	}

	public void saveAll(List<Person> listPersons) {
		personRepository.saveAll(listPersons);

	}
}
