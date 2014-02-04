/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kaibot.springrest.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author kaiak31
 */
@Entity
public class Activity{

@Id
@GeneratedValue(strategy=GenerationType.AUTO)
private Long id;
@Column(nullable = false)
private Long merchantId;

@Column(nullable = true)
private Integer capacity;

@Column(nullable = false)
private String description;

@Column(nullable=true)
private Integer duration;

protected Activity() {}

    //It would be better to use a Builder pattern for this but that is beyond the scope of this project
public Activity(Long merchantId, String description, Integer duration){
      this.setMerchantId(merchantId);
      this.description = description;
      this.duration = duration;
}


public Activity(Long merchantId, String description){
       this(merchantId, description, 0);
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

    @Override
    public boolean equals(Object o) {
        if(o instanceof Activity){
            return (this.getMerchantId().equals(((Activity) o).getMerchantId()) && this.getDescription().equals(((Activity)o).getDescription()));
        }
        return false;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }


    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
