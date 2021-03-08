package com.project.project.repository;



import org.springframework.data.repository.CrudRepository;

import com.project.project.models.Example;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface ExampleRepository extends CrudRepository<Example, Integer> {
}