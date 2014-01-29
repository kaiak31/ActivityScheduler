/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kaibot.springrest.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import org.joda.time.DateTime;

/**
 *
 * @author kaiak31
 */
@Entity
public class Slot {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    @Column(nullable = true)
    private Integer spaces;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column
    private Date date;
    
    @ManyToOne
    @JoinColumn(name = "activityId", nullable = false)
    private Activity activity;

    public Slot(){}
    
    public Slot(Activity activity, DateTime date){
        this.date = date.toDate();
        this.activity = activity;
        this.setSpaces(activity.getCapacity());
    }
    
     public Slot(Activity activity, DateTime date, int spaces){
        this.date = date.toDate();
        this.activity = activity;
        this.setSpaces(spaces);
    }
            
    
    /**
     * @return the activity
     */
    public Activity getActivity() {
        return activity;
    }

    /**
     * @param activity the activity to set
     */
    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    /**
     * @return the scheduledDate
     */
    public DateTime getScheduledDate() {
        return new DateTime(date);
    }

    /**
     * @param scheduledDate the scheduledDate to set
     */
    public void setScheduledDate(DateTime scheduledDate) {
        this.date = scheduledDate.toDate();
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSpaces() {
        return spaces;
    }

    public void setSpaces(Integer spaces) {
        this.spaces = spaces;
    }
}
