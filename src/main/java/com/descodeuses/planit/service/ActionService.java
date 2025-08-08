package com.descodeuses.planit.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

// import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.descodeuses.planit.dto.ActionDTO;
import com.descodeuses.planit.model.Action;
import com.descodeuses.planit.model.Contact;
import com.descodeuses.planit.model.Projet;
import com.descodeuses.planit.model.Utilisateur;
import com.descodeuses.planit.repository.ActionRepository;
import com.descodeuses.planit.repository.ContactRepository;
import com.descodeuses.planit.repository.ProjetRepository;
import com.descodeuses.planit.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ActionService {

    private final ActionRepository repository;
    private final ContactRepository contactRepository;
    private final ProjetRepository projetRepository;
    private final UserRepository userRepository;

    public ActionService(ActionRepository repository, ContactRepository contactRepository, ProjetRepository projetRepository, UserRepository userRepository){
        this.repository = repository;
        this.contactRepository = contactRepository;
        this.projetRepository = projetRepository;
        this.userRepository = userRepository;
    }

    private ActionDTO convertToDTO(Action action){
        ActionDTO dto =  new ActionDTO(
            action.getId(), 
            action.getTitle(),
            action.getCompleted(),
            action.getDueDate(),
            action.getPriorite()
        );

        dto.setProjetId(action.getProjet()!=null?action.getProjet().getId():null);

        Set<Long> memberIds = action.getMembers().stream()
        .map(Contact::getId)
        .collect(Collectors.toSet());

        dto.setMemberIds(memberIds);
        dto.setUserId(action.getUser() != null ? action.getUser().getId() : null);
        return dto;
    }
    

    private Action convertToEntity(ActionDTO actionDTO, Set<Contact> members) {
        Action action = new Action();
        action.setId(actionDTO.getId());
        action.setTitle(actionDTO.getTitle());
        action.setCompleted(actionDTO.getCompleted());
        action.setDueDate(actionDTO.getDueDate());
        action.setPriorite(actionDTO.getPriorite());
        action.setMembers(members);
        if (actionDTO.getProjetId()!=null) {
            Projet projet = projetRepository.findById(actionDTO.getProjetId()).orElseThrow(()->new EntityNotFoundException("Projet not found with id: "+actionDTO.getProjetId()));
            action.setProjet(projet);
        }

        return action;
    }

    public List<ActionDTO> getAllActions(){
        UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Utilisateur user = userRepository.findByUsername(username).orElseThrow(()->new EntityNotFoundException("User not found"));
        List<Action> actions = repository.findByUserId(user.getId());
        //Declarer une variable liste de action DTO
        List<ActionDTO> actionDTOList = new ArrayList<>();
        //Faire boucle sur la liste action
        for (Action elem : actions) {
            //Convertir action vers action dto
            //Ajouter action dto dans la liste
            actionDTOList.add(convertToDTO(elem));
        }
        // Retourner la liste
        return actionDTOList;
    }

    public ActionDTO getActionById(Long id){
        // Action action = repository.findById(id).orElseThrow(()->new EntityNotFoundException("Action not found with id: "+id));
        Optional<Action> action = repository.findById(id);
        if(action.isEmpty()){
            throw new EntityNotFoundException("Action not found with id: "+id);
        }

        return convertToDTO(action.get());
    }

    public ActionDTO postAction(ActionDTO dto){
        // UserDetails userDetails = (userD)
        UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Utilisateur user = userRepository.findByUsername(username).orElseThrow(()->new EntityNotFoundException("User not found"));

        // Convertir le DTO en entité
        //entité = convertirVersEntité(dto)
        Set<Contact> contacts = new HashSet<>(contactRepository.findAllById(dto.getMemberIds()));

        Action action = convertToEntity(dto,contacts);
        action.setUser(user);
        

        // Sauvegarder l'entité dans la base de données
        //entitéEnregistrée = référentiel.sauvegarder(entité)
        Action savedAction = repository.save(action);

        // Convertir l'entité enregistrée en DTO et retourner
        //retourner convertirVersDTO(entitéEnregistrée)
        return convertToDTO(savedAction);
    }


    public ActionDTO update(Long id, ActionDTO dto) {
        // Rechercher l'entité par son identifiant
        // entitéExistante = référentiel.trouverParId(id)
        Action existante = repository.findById(id).orElseThrow(()->new EntityNotFoundException("Action not found with id: "+id));
        //    sinon lever une exception "Ressource non trouvée"

        // Mettre à jour les champs de l'entité avec les valeurs du DTO
        existante.setTitle(dto.getTitle());
        existante.setCompleted(dto.getCompleted());
        existante.setDueDate(dto.getDueDate());
        existante.setPriorite(dto.getPriorite());

        Set<Contact> contacts = new HashSet<>(contactRepository.findAllById(dto.getMemberIds()));
        existante.setMembers(contacts);

        // update projet
        if (dto.getProjetId()!=null) {
            Projet projet = projetRepository.findById(dto.getProjetId()).orElseThrow(()->new EntityNotFoundException("Projet not found with id: "+dto.getProjetId()));
            existante.setProjet(projet);
        }
        else{
            existante.setProjet(null);
        }

        // Sauvegarder l'entité mise à jour dans la base de données
        //entitéMiseAJour = référentiel.sauvegarder(entitéExistante)
        Action existanteMiseAJour = repository.save(existante);

        // Convertir l'entité mise à jour en DTO et retourner
        //retourner convertirVersDTO(entitéMiseAJour)
        return convertToDTO(existanteMiseAJour);
    }

    public void delete(Long id) {
        // Vérifier si une entité avec l'identifiant donné existe
        //si référentiel.n'existePasParId(id) alors
        //   lever une exception "Ressource non trouvée avec cet id"
        Action existante = repository.findById(id).orElseThrow(()->new EntityNotFoundException("Action not found with id: "+id));
        repository.delete(existante);

        // Supprimer l'entité par son identifiant
        //référentiel.supprimerParId(id)
    } 
}
