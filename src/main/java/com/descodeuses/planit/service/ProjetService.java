package com.descodeuses.planit.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.descodeuses.planit.dto.ProjetDTO;
import com.descodeuses.planit.model.Projet;
import com.descodeuses.planit.repository.ProjetRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProjetService {
    private final ProjetRepository repository;

    public ProjetService(ProjetRepository repository){
        this.repository = repository;
    }

    private ProjetDTO convertToDTO(Projet project){
        ProjetDTO dto = new ProjetDTO(
            project.getId(),
            project.getTitle(),
            project.getDescription());
        return dto;
    }

    private Projet convertToEntity(ProjetDTO ProjetDTO){
        Projet project = new Projet();
        project.setId(ProjetDTO.getId());
        project.setTitle(ProjetDTO.getTitle());
        project.setDescription(ProjetDTO.getDescription());

        return project;
    }

    public List<ProjetDTO> getAllProjet(){
        List<Projet> Projet = repository.findAll();
        List<ProjetDTO> ProjetDTOLists = new ArrayList<>();
        for (Projet elem : Projet) {
            ProjetDTOLists.add(convertToDTO(elem));
        }
        return ProjetDTOLists;

    }

    public ProjetDTO getProjetById(Long id){
        Optional<Projet> project = repository.findById(id);
        if (project.isEmpty()) {
            throw new EntityNotFoundException("Projet not found: "+id);
        }

        return convertToDTO(project.get());
    }

    public ProjetDTO create(ProjetDTO dto){
        Projet project = convertToEntity(dto);
        Projet savedProjet = repository.save(project);

        return convertToDTO(savedProjet);
    }

    public ProjetDTO update(Long id, ProjetDTO dto){
        System.out.println(dto.getDescription());
        Projet existante = repository.findById(id).orElseThrow(()->new EntityNotFoundException("Project not found with id: "+id));
        existante.setTitle(dto.getTitle());
        existante.setDescription(dto.getDescription());
        Projet miseAJour = repository.save(existante);

        return convertToDTO(miseAJour);
    }

    public void delete(Long id){
        Projet existante = repository.findById(id).orElseThrow(()->new EntityNotFoundException("Project not found with id: "+id));
        repository.delete(existante);
    }
}
