package com.example.lab3.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "models")
public class models {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long ID_Model;

    @NotBlank(message = "Name model is required!")
    private String Name_Model;

    public models(){}

    public models(String Name_Model){
        this.Name_Model = Name_Model;
    }

    public long getID_Model() {
        return ID_Model;
    }

    public void setID_Model(long ID_Model) {
        this.ID_Model = ID_Model;
    }

    public String getName_Model() {
        return Name_Model;
    }

    public void setName_Model(String name_Model) {
        Name_Model = name_Model;
    }
}
