/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kaibot.springrest.service;

import com.kaibot.springrest.entity.Activity;
import com.kaibot.springrest.entity.Slot;
import com.kaibot.springrest.repository.ActivityRepository;
import com.kaibot.springrest.repository.SlotRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;
import org.joda.time.DateTime;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 *
 * @author kaiak31
 */
@Service("ScheduleServiceIF")
@Transactional
@Repository
public class ScheduleServiceImpl implements ScheduleServiceIF, InitializingBean{
    @Autowired
    private ActivityRepository activityRepo;
    
    @Autowired
    private SlotRepository slotRepo;
    
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    private EntityManager entityManager;

    @Override
    public void afterPropertiesSet() throws Exception {
        setEntityManager(entityManagerFactory.createEntityManager());
    }
    
    @Override
    public List<Slot> checkAvailiablityDay(DateTime date, Activity activity) {
        DateTime start = date.withTimeAtStartOfDay();
        DateTime finish = start.plusDays(1);
        List<Slot> raw = slotRepo.findByActivityIdAndDateRange(activity.getId(), start.toDate(), finish.toDate());
        List<Slot> available = new ArrayList<>();
        for(Slot slot: raw){
            if(slot.getSpaces() == null || slot.getSpaces() > 0){
                available.add(slot);
            }
        }
        return available;
    }

    @Override
    public List<Slot> checkAvailibilityByDateRange(DateTime start, DateTime end, Activity activity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean createBooking(DateTime date, Activity activity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @return
     */
    @Override
    @Transactional
    public List<Activity> findAllActivities() {
       return activityRepo.findAll();
    }

    /**
     * @return the entityManager
     */
    public EntityManager getEntityManager() {
        return entityManager;
    }

    /**
     * @param entityManager the entityManager to set
     */
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public  <T extends Object> T reattach(T obj) {
        return entityManager.merge(obj);
    }

    @Override
    public <T> void save(T obj) {
        entityManager.persist(obj);
    }

    @Override
    public boolean removeAvailability(Activity activity, DateTime date) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addAvailability(Activity activity, DateTime date) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addAvailabilityRange(Activity activity, DateTime start, DateTime end) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
    
}
