/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kaibot.springrest.service;

import com.kaibot.springrest.entity.Activity;
import com.kaibot.springrest.entity.Slot;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EntityExistsException;

import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

/**
 *
 * @author kaiak31
 */

public interface ScheduleServiceIF{
    public Activity createActivity(Long merchantId, String description,Integer minutes) throws EntityExistsException;
    public List<Slot> checkAvailabilityDay(DateTime date,Long activityId);
    public List<Slot> checkAvailabilityByDateRange(Long activityId, DateTime start, DateTime end);
    public Slot bookSlot(Long slotId, int spaces);
    public Slot addSlot(Long activityId, DateTime date, Long ownerId);
    public Slot editSlot(Long slotId, Long ownerId, int duration, int spaces);
    public List<Activity> findAllActivities();
    public Slot saveOrUpdateSlot(Slot slot);
    public Activity saveOrUpdateActivity(Activity edit);
    public <T extends Object> T reattach(T obj);
    public <T extends Object> void save( T obj);


}
