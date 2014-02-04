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
import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
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
    private DateTime date;

    @Override
    public void afterPropertiesSet() throws Exception {
        setEntityManager(entityManagerFactory.createEntityManager());
    }

    @Override
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public Activity createActivity(Long merchantId, String description, int minutes) {
        if(activityRepo.findByMerchantIdAndDescription(merchantId,description)!= null){
            throw new EntityExistsException();
        }
        Activity activity = new Activity(merchantId,description, minutes);
        activity = activityRepo.save(activity);
        return activity;
    }

    @Override
    public List<Slot> checkAvailabilityDay(DateTime date, Long activityId) {
        DateTime start = date.withTimeAtStartOfDay();
        DateTime finish = start.plusDays(1);
        List<Slot> raw = slotRepo.findByActivityIdAndDateRange(activityId, start.toDate(), finish.toDate());
        List<Slot> available = new ArrayList<>();
        for(Slot slot: raw){
            if(slot.getSpaces() == null || slot.getSpaces() > 0){
                available.add(slot);
            }
        }
        return available;
    }

    @Override
    public List<Slot> checkAvailabilityByDateRange(Activity activity, DateTime start, DateTime end) {
           return slotRepo.findByActivityIdAndDateRange(activity.getId(), start.toDate(), end.toDate());
    }


    @Transactional(Transactional.TxType.REQUIRES_NEW)
    private Slot createSlot(Long activityId,DateTime date, Long ownerId) {
        Activity activity = this.activityRepo.findOne(activityId);
        if(activity == null){
            throw new EntityNotFoundException();
        }
        if(activity.getDuration()>0){
           // check to see if a slot within this time.
           DateTime startTime = date.minusMinutes(activity.getDuration());
           DateTime endTime = date.plusMinutes(activity.getDuration());
           List<Slot> slots;
           if(ownerId != null){
               slots = slotRepo.findByActivityIdAndDateRangeAndOwnerId(activity.getId(),startTime.toDate(), endTime.toDate(), ownerId );
           }else{
               slots = slotRepo.findByActivityIdAndDateRangeAndNullOwnerId(activity.getId(),startTime.toDate(), endTime.toDate() );

           }

               if(!slots.isEmpty()){
               throw new EntityExistsException();
           }
        }
        Slot slot = new Slot(activity,date);
        if (ownerId != null){
            slot.setOwnerId(ownerId);
        }
        slot = slotRepo.save(slot);
        return slot;
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
    public Slot bookSlot(Long slotId, int spaces) {
        Slot slot = slotRepo.findOne(slotId);
        int remaining = slot.getSpaces() - spaces;
        if(remaining < 0){
            throw new IllegalArgumentException("Not enough spaces for booking");
        }
        else{
            slot.setSpaces(remaining);
            slot = slotRepo.save(slot);
        }
        return slot;
    }

    @Override
    public Slot addSlot(Long activityId, DateTime date, Long ownerId) {
        Slot slot = null;
        try{
            slot =  this.createSlot(activityId,date, ownerId);
            return slot;
        }
        catch(EntityExistsException ex){
            return null;
        }
    }



}
