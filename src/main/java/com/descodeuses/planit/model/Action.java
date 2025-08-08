package com.descodeuses.planit.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="todo")
public class Action {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String title;

    private boolean completed;
    private LocalDate dueDate;

    @Column(length=255)
    private String priorite;

    // ---------Todo--------------------

    public Long getId() {
        return id;
    }

    // public Action(Long id, String name) {
    //     this.id = id;
    //     this.name = name;
    // }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getPriorite() {
        return priorite;
    }

    public void setPriorite(String priorite) {
        this.priorite = priorite;
    }

    // -------------------todo & contact---------
    @ManyToMany
    @JoinTable(
        name="todo_contact",
        joinColumns=@JoinColumn(name="todo_id"),
        inverseJoinColumns=@JoinColumn(name="contact_id")
    )

    public Set<Contact> members = new HashSet<>();

    public Set<Contact> getMembers(){
        return members;
    }

    public void setMembers(Set<Contact> members){
        this.members = members;
    }

    // --------Many to one -------------------
    
    @ManyToOne
    @JoinColumn(name="projet_id", referencedColumnName="id")
    private Projet projet;

    public Projet getProjet() {
        return projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }

    // ---------user id-------------
    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName="id")
    private Utilisateur user;

    public Utilisateur getUser() {
        return user;
    }

    public void setUser(Utilisateur user) {
        this.user = user;
    }

    
}
