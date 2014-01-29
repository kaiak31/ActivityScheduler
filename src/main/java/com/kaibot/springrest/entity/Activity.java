/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kaibot.springrest.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.transaction.Transactional;

/**
 *
 * @author kaiak31
 */
@Entity
public class Activity {

@Id
@GeneratedValue(strategy=GenerationType.AUTO)
private Long id;
@Column(nullable = false)
private Long merchantId;

@Column(nullable = true)
private Integer capacity;

@Column(nullable = false)
private String description;

@OneToMany(fetch = FetchType.LAZY,cascade=CascadeType.ALL, mappedBy="activity")
private List<Slot> slots; 

protected Activity() {}

public Activity(Long merchantId, String description){
      this.merchantId = merchantId;
      this.description = description;
}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }
    
    public String toString(){
        return "Activity: "+description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    public void addSlot(Slot slot){
        if(this.slots == null){
            this.slots = new ArrayList<>();
        }
        this.slots.add(slot);
    }

    /**
     * @return the slots
     */
    public List<Slot> getSlots() {
        return slots;
    }

    /**
     * @param slots the slots to set
     */
    public void setSlots(List<Slot> slots) {
        this.slots = slots;
    }

    /**
     * @return the capacity
     */
    public Integer getCapacity() {
        return capacity;
    }

    /**
     * @param capacity the capacity to set
     */
    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
}
