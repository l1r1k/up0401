package com.example.lab3.models;

import javax.persistence.*;

@Entity
@Table(name = "marks")
public class marks {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long ID_Mark;

    private String Name_Mark;

    public marks(){}

    public marks(String Name_Mark){
        this.Name_Mark = Name_Mark;
    }

    public long getID_Mark() {
        return ID_Mark;
    }

    public void setID_Mark(long ID_Mark) {
        this.ID_Mark = ID_Mark;
    }

    public String getName_Mark() {
        return Name_Mark;
    }

    public void setName_Mark(String name_Mark) {
        Name_Mark = name_Mark;
    }
}
