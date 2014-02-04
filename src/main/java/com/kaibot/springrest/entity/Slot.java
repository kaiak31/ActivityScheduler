/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kaibot.springrest.entity;

import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.Date;

/**
 * @author kaiak31
 */

@Table(
        uniqueConstraints=
        @UniqueConstraint(columnNames={"activityId", "ownerId"})
)
@Entity
public class Slot {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @Column(nullable = true)
    private Long ownerId;

    @Column(nullable = true)
    private Integer spaces;

    @Temporal(javax.persistence.TemporalType.DATE)
    @Column
    private Date date;

    @ManyToOne
    @JoinColumn(name = "activityId", nullable = false)
    private Activity activity;

    protected Slot(){}

    public Slot(Activity activity, DateTime date) {
        this.activity = activity;
        this.setSpaces(activity.getCapacity());
        this.date = date.toDate();
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

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Slot) {
            Slot s1 = (Slot) obj;
            if (s1.id.longValue() == this.id.longValue()) {
                return true;
            }
            if (s1.activity.equals(this.activity)) {
                if (s1.ownerId.longValue() == this.ownerId.longValue()) {
                    return true;
                }
            }
        }
        return false;
    }
}
