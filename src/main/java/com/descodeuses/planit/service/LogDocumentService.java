package com.descodeuses.planit.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.descodeuses.planit.dto.ActionDTO;
import com.descodeuses.planit.dto.AuthRequest;
import com.descodeuses.planit.model.LogDocument;
import com.descodeuses.planit.model.Utilisateur;
import com.descodeuses.planit.repository.LogDocumentRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class LogDocumentService {
    @Autowired
    private LogDocumentRepository repo;
    
    // action
    public void addLog(String text, HttpServletRequest request, ActionDTO action) {
        LogDocument doc = new LogDocument();
        doc.setText(text);
        doc.setTimestamp(LocalDateTime.now());
        Map<String, Object> extras = new HashMap<>();
        extras.put("action", action.getTitle());
        extras.put("user", action.getUserId());
        doc.setExtras(extras);

        repo.save(doc);
    }

    // Login
    public void addLog(String text, HttpServletRequest request, AuthRequest authRequest, Utilisateur user) {
        LogDocument doc = new LogDocument();
        doc.setText(text);
        doc.setTimestamp(LocalDateTime.now());

        // Map<String, Object> extras = Map.of(
        // "request", Map.of(
        // "username", authRequest.getUsername(),
        // "_class", authRequest.getClass().getName()
        // ),
        // "user", Map.of(
        // "username", user.getUsername(),
        // "password", user.getPassword(),
        // "role", user.getRole()
        // )
        // );
        Map<String, Object> extras = new HashMap<>();
        extras.put("username", user.getUsername());
        extras.put("password", user.getPassword());
        doc.setExtras(extras);

        repo.save(doc);
    }
}
