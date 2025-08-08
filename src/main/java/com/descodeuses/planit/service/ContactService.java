package com.descodeuses.planit.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.descodeuses.planit.dto.ContactDTO;
import com.descodeuses.planit.model.Contact;
import com.descodeuses.planit.repository.ContactRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ContactService {
    private final ContactRepository repository;

    public ContactService(ContactRepository repository){
        this.repository = repository;
    }

    private ContactDTO convertContact(Contact contact){
        return new ContactDTO(
            contact.getId(),
            contact.getNom(),
            contact.getPrenom(),
            contact.getAge()
        );
    }

    private Contact convertEntity(ContactDTO contactDto){
        Contact contact = new Contact();
        contact.setId(contactDto.getId());
        contact.setNom(contactDto.getNom());
        contact.setPrenom(contactDto.getPrenom());
        contact.setAge(contactDto.getAge());
        return contact;
    }

    public List<ContactDTO> getAllContacts(){
        List<Contact> contact = repository.findAll();
        List<ContactDTO> contactDtoList = new ArrayList<>();
        for (Contact elem : contact) {
            contactDtoList.add(convertContact(elem));
        }
        return contactDtoList;
    }

    public ContactDTO getContact(Long id){
        Optional<Contact> contact = repository.findById(id);
        if(contact.isEmpty()){
            throw new EntityNotFoundException("Contact Not Found with id: "+id);
        }
        return convertContact(contact.get());
    }

    public ContactDTO create(ContactDTO dto){
        Contact entity = convertEntity(dto);
        Contact savedEntity = repository.save(entity);
        return convertContact(savedEntity);
    }
}
