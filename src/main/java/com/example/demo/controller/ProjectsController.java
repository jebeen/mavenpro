package com.example.demo.controller;

import com.example.demo.models.Projects;
import com.example.demo.repository.ProjectsRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@RequestMapping("api/projects")
@CrossOrigin(origins = "http://localhost:3000/", maxAge = 3600)
@RestController
public class ProjectsController {

    
    private final ProjectsRepository project;
    // Connect the repository to the controller
   

    public ProjectsController(ProjectsRepository Repository) {
        this.project = Repository;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
		List<Projects> result = (List<Projects>) project.findAll();
		return new ResponseEntity(result, HttpStatus.OK);
	}

    // DELETE
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable Integer id) {
        Optional<Projects> del = this.project.findById(id);
        
        if (del.isPresent()) {
            this.project.delete(del.get());
            System.out.println("Item"+id);
            List<Projects> result = (List<Projects>) project.findAll();
		    return new ResponseEntity(result, HttpStatus.OK);
        } else {
            List<Projects> result = (List<Projects>) project.findAll();
            return new ResponseEntity(result, HttpStatus.NOT_FOUND);
        }
    }

    // UPDATE
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<?> projectUpdate(@RequestBody Projects proj, @PathVariable Integer id) {
        Optional<Projects> UpdateOptional = this.project.findById(id);
        if (UpdateOptional.isPresent()) {
            Projects p = UpdateOptional.get();
            if (proj.getDescription() != null) {
                p.setDescription(proj.getDescription());
            }
            if (proj.getNo_of_months() != null) {
                p.setNo_of_months(proj.getNo_of_months());
            }
            if (proj.getNo_of_years() != null) {
                p.setNo_of_years(proj.getNo_of_years());
            }
            if (proj.getProjectname() != null) {
                p.setProjectname(proj.getProjectname());
            }
            this.project.save(p);
            List<Projects> result = (List<Projects>) project.findAll();
            return new ResponseEntity(result, HttpStatus.OK);
        } else {
            List<Projects> result = (List<Projects>) project.findAll();
            return new ResponseEntity(result, HttpStatus.NOT_FOUND);
        }
    }


    // READ
    @GetMapping(path = "/{id}")
    public Optional<Projects> getProjectById(@PathVariable Integer id) {
        return this.project.findById(id);
    }

    // CREATE
    @PostMapping(path = "/add")
    public Projects addProject(@RequestBody Projects p) {
        return this.project.save(p);
    }

    
    
}
