package com.descodeuses.planit.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.descodeuses.planit.dto.ActionDTO;
import com.descodeuses.planit.model.Action;
import com.descodeuses.planit.service.ActionService;
import com.descodeuses.planit.service.LogDocumentService;

import jakarta.servlet.http.HttpServletRequest;




// import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/action")
public class ActionController {
    private final ActionService service;

   
    private final LogDocumentService logService;

    public ActionController(ActionService service, LogDocumentService logService){
        this.service = service;
        this.logService = logService;
    }

    @GetMapping("/action/postgresql")
    public ResponseEntity<List<ActionDTO>> actionPostgreSql(){
        List<ActionDTO> list = service.getAllActions();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    @GetMapping("/action/postgresql/{id}")
    public ResponseEntity<ActionDTO> actionPostgreSqlById(@PathVariable Long id){
        ActionDTO action = service.getActionById(id);
        return new ResponseEntity<ActionDTO>(action, HttpStatus.OK);
    }
    @PostMapping("/action/postgresql")
    public ResponseEntity<ActionDTO> createAction(@RequestBody ActionDTO requestDTO, HttpServletRequest httpRequest) {       
        ActionDTO createdDTO = service.postAction(requestDTO);
        logService.addLog("Action Created", httpRequest, createdDTO);
        return new ResponseEntity<>(createdDTO, HttpStatus.CREATED);
    }

    @PutMapping("/action/postgresql/{id}")
    public ResponseEntity<ActionDTO> updateAction(@PathVariable Long id, @RequestBody ActionDTO requestDTO) {
        ActionDTO updateDTO = service.update(id, requestDTO);
        return new ResponseEntity<>(updateDTO, HttpStatus.OK);
    }
    
    @DeleteMapping("/action/postgresql/{id}")
    public ResponseEntity<Void> deleteActionPostgre(@PathVariable Long id) {
        service.delete(id); // Perform deletion
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/hello")
    public String Hello() {
        // ArrayList<Integer> liste = new ArrayList<>();
        // liste.add(1);
        // liste.add(2);
        // liste.add(3);
        // liste.get(0);

        return "Hello !";
    }

    @GetMapping("/goodBye")
        public String GoodBye(){
        return "Good Bye!";
    }

    @GetMapping
    public String getAction(){
        return "Retour post all";
    }

    @GetMapping("/{id}")
    public String getActionById(){
        return "Retour post one";
    }

    @PostMapping
    public String createAction(){
        return "Retour post";
    }

    @PatchMapping
    public String updateAction(){
        return "Retour update";
    }

    @DeleteMapping
    public String deleteAction(){
        return "Retour delete";
    }


    // @GetMapping("/actions-list")
    // public List<Action> getAllActions() {
    //     List<Action> actions = new ArrayList<>();
    //     actions.add(new Action(1, "name 1"));
    //     actions.add(new Action(2, "name 2"));
    //     actions.add(new Action(3, "name 3"));
    //     return actions;
    // } 
    
    @GetMapping("/actions-lists")
    public ResponseEntity<ArrayList<Action>> getAll() {
        Action action1 = new Action();
        action1.setTitle("Envoyer un mail");
        action1.setId(1L);
        Action action2 = new Action();
        action2.setTitle("Postuler Candidate");
        action2.setId(2L);

        ArrayList<Action> list = new ArrayList<>();
        list.add(action1);
        list.add(action2);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/actions-lists/{id}")
    public ResponseEntity<Action> getActionById(@PathVariable Long id) {
        Action action = new Action();
        action.setTitle("Faire de cours");
        return new ResponseEntity<>(action, HttpStatus.OK);
    }

    @PostMapping("/actions-lists")
    public String createAction(@RequestBody Action item) {
        return item.getTitle();
    }
    
    @PutMapping("/actions-lists/{id}")
    public String updateAction(@PathVariable Long id, @RequestBody Action item) {

        return item.getTitle();
    }
    
    @DeleteMapping("/actions-lists")
    public String deleteAction(@PathVariable Long id){
        return "Delete Contact";
    }
    
    
    
}
