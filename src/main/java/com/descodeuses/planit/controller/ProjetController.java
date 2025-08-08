package com.descodeuses.planit.controller;
import org.springframework.web.bind.annotation.RestController;

import com.descodeuses.planit.service.ProjetService;

import com.descodeuses.planit.dto.ProjetDTO;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/project")
public class ProjetController {
    private final ProjetService service;

    public ProjetController(ProjetService service){
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ProjetDTO>> getProjects() {
        List<ProjetDTO> list = service.getAllProjet();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjetDTO> getProjectBYId(@PathVariable Long id) {
        ProjetDTO project = service.getProjetById(id);
        return new ResponseEntity<ProjetDTO>(project, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProjetDTO> create(@RequestBody ProjetDTO requesDTO) {
        ProjetDTO createDto = service.create(requesDTO);
        return new ResponseEntity<>(createDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjetDTO> updateProject(@PathVariable Long id, @RequestBody ProjetDTO requesDTO) {
        System.out.println("requesDTO: " +requesDTO);
        ProjetDTO updateProject = service.update(id, requesDTO);
        return new ResponseEntity<>(updateProject, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id){
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    
}
