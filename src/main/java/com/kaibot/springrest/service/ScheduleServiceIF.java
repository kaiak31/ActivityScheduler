/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kaibot.springrest.service;

import com.kaibot.springrest.entity.Activity;
import com.kaibot.springrest.entity.Slot;
import java.util.List;
import javax.persistence.Entity;
import org.joda.time.DateTime;

/**
 *
 * @author kaiak31
 */
public interface ScheduleServiceIF{
    public List<Slot> checkAvailiablityDay( DateTime date,Activity activity);
    public List<Slot> checkAvailibilityByDateRange(DateTime start, DateTime end, Activity activity);
    public boolean createBooking(DateTime date, Activity activity);
    public boolean removeAvailability(Activity activity, DateTime date);
    public boolean addAvailability(Activity activity, DateTime date);
    public void addAvailabilityRange(Activity activity, DateTime start, DateTime end);
    public List<Activity> findAllActivities();
    public <T extends Object> T reattach(T obj);
    public <T extends Object> void save( T obj);


}
