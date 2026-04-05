package com.example.demo.repository;
import org.springframework.data.repository.CrudRepository;
import com.example.demo.models.Projects;

public interface ProjectsRepository extends CrudRepository<Projects, Integer> {

}
