package com.descodeuses.planit.exerciseTp;

import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

class Taches {
    private String nomTache;

    public String getNomTache() {
        return nomTache;
    }

    public void setNomTache(String nomTache) {
        this.nomTache = nomTache;
    }

    private Boolean completed;

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    private LocalDate dueDate;

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

}

public class exercise1 {
    public static void main(String[] args){
        System.out.println("----Tache----");
        Scanner input = new Scanner(System.in);
        int isContinue;
        ArrayList<Taches> listOfTache = new ArrayList<>();
        do {
            Taches tache = new Taches();
            System.out.println("Le Tache: ");
            tache.setNomTache(input.nextLine());
            System.out.println("Completed? ");
            tache.setCompleted(input.nextBoolean());
            input.nextLine();
            System.out.println("Due Date: ");
            String date = input.nextLine();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate dueDate = LocalDate.parse(date, formatter);
            tache.setDueDate(dueDate);
            listOfTache.add(tache);
            System.out.println("Continue or break(0 = Contine or 1 = break): ");
            isContinue = input.nextInt();
            input.nextLine();
        } while (isContinue == 0);

        try {
            FileWriter file = new FileWriter("Taches.csv");
            file.write("Nom De Tache,Completed,Due Date\n"); // Header line

            for (Taches tache : listOfTache) {
                file.write(
                    tache.getNomTache() + "," +
                    tache.getCompleted() + "," +
                    tache.getDueDate() + "\n"
                );
            }
            file.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        for(Taches tache:listOfTache){
            System.out.println("Le Tache: "+tache.getNomTache()+" Completed: "+tache.getCompleted());
            
            
        }
        
        System.out.println("Do you want to delete? Give Nom de Tache: ");
        String nomDeTache = input.nextLine();
        listOfTache.removeIf(tache-> tache.getNomTache().equals(nomDeTache));

        System.out.println("After delete "+nomDeTache+" .The rest of taches are the following of taches: ");
        for(Taches tache:listOfTache){
            System.out.println("Le Tache: "+tache.getNomTache()+" Completed: "+tache.getCompleted());
        }
        
        input.close();

    }
}
