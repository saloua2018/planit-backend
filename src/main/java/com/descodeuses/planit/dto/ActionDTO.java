package com.descodeuses.planit.dto;

import java.time.LocalDate;
import java.util.Set;


public class ActionDTO {
    private Long id;
    private String title;
    private boolean completed;
    private LocalDate dueDate;
    private String priorite;
    private Set<Long> memberIds;

    public Set<Long> getMemberIds(){
        return memberIds;
    }

    public void setMemberIds(Set<Long> memberIds){
        this.memberIds =  memberIds;
    }

    private Long projetId;

    public Long getProjetId() {
        return projetId;
    }

    public void setProjetId(Long projetId) {
        this.projetId = projetId;
    }

    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public ActionDTO(Long id,String title,boolean completed,LocalDate dueDate,String priorite){
        this.id = id;
        this.title = title;
        this.completed = completed;
        this.dueDate = dueDate;
        this.priorite = priorite;
    }

    public Long getId() {
        return id;
    }

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

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    
}
