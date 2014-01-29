/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kaibot.springrest.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author kaiak31
 */
@Entity
public class Merchant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String owner;
    String title;
    
    public Merchant(){}
    public Merchant(String owner, String title){
        this.owner = owner;
        this.title = title;
    }
}
