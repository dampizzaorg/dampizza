/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dampizza.logic.dto;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * User Data Transfer Object
 *
 * @author Carlos Santos
 */
public class UserDTO {

    private SimpleLongProperty id;
    private SimpleStringProperty username;
    private SimpleStringProperty name;
    private SimpleStringProperty surnames;
    private SimpleStringProperty email;
    private SimpleStringProperty address;

    public UserDTO() {
        this.id = new SimpleLongProperty();
        this.username = new SimpleStringProperty();
        this.name = new SimpleStringProperty();
        this.surnames = new SimpleStringProperty();
        this.email = new SimpleStringProperty();
        this.address = new SimpleStringProperty();
    }

    public UserDTO(String username, String name, String surnames, String email, String address) {
        this.id = new SimpleLongProperty();
        this.username = new SimpleStringProperty(username);
        this.name = new SimpleStringProperty(name);
        this.surnames = new SimpleStringProperty(surnames);
        this.email = new SimpleStringProperty(email);
        this.address = new SimpleStringProperty(address);
    }
    
    public UserDTO(Long id,String username, String name, String surnames, String email, String address) {
        this.id = id != null ? new SimpleLongProperty(id) : new SimpleLongProperty();
        this.username = new SimpleStringProperty(username);
        this.name = new SimpleStringProperty(name);
        this.surnames = new SimpleStringProperty(surnames);
        this.email = new SimpleStringProperty(email);
        this.address = new SimpleStringProperty(address);
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username.get();
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username.set(username);
    }

    /**
     * @return the name
     */
    public String getName() {
        return name.get();
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name.set(name);
    }

    /**
     * @return the surnames
     */
    public String getSurnames() {
        return surnames.get();
    }

    /**
     * @param surnames the surnames to set
     */
    public void setSurnames(String surnames) {
        this.surnames.set(surnames);
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email.get();
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email.set(email);
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address.get();
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address.set(address);
    }
    
    

//    @Override
//    public String toString() {
//        return "username: " + this.getUsername() + ", name: " + this.getName()
//                + ", surnames: " + this.getSurnames() + ", email: " + this.getEmail()
//                + ", address: " + this.getAddress();
//    }
    
    @Override
    public String toString() {
//        String firsSurname= this.getSurnames().substring(0, this.getSurnames().indexOf("%")-1);
//        String secondSurname= this.getSurnames().substring(this.getSurnames().indexOf("%")+1);
        
        //return this.getId()+", "+this.getName()+", "+firsSurname+" "+secondSurname;
        return this.getId()+", "+this.getName();
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id.get();
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id.set(id);
    }
    
    

}
