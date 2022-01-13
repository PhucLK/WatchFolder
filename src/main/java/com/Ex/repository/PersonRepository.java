package com.Ex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Ex.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

	Person findById(int id);

}
