package com.descodeuses.planit.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.descodeuses.planit.dto.ContactDTO;
import com.descodeuses.planit.service.ContactService;

@RestController //--annotation--
@RequestMapping("/api/contact")
public class ContactController {
    public final ContactService service;

    public ContactController(ContactService service){
        
        this.service = service;
    }
    @GetMapping
    public ResponseEntity<List<ContactDTO>> getContacts(){
        List<ContactDTO> contact = service.getAllContacts();
        return new ResponseEntity<>(contact, HttpStatus.OK);
    }
    // @GetMapping
    // public String getContacts(){
    //     return "get contacts";
    // }

    @GetMapping("/{id}")
    public String getContactById(){
        return "get contact by id";
    }

    @PostMapping
    public String createContact(){
        return "create contacts";
    }
    // @PostMapping
    // public String createContact(){
    //     return "create contacts";
    // }

    @PatchMapping
    public String updateContact(){
        return "update contacts";
    }

    @DeleteMapping
    public String deleteContact(){
        return "delete contacts";
    }


}
