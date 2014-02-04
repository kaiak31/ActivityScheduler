package com.kaibot.springrest.test;

import com.kaibot.springrest.Application;
import com.kaibot.springrest.entity.Activity;
import com.kaibot.springrest.entity.Slot;
import com.kaibot.springrest.repository.ActivityRepository;
import com.kaibot.springrest.repository.SlotRepository;
import com.kaibot.springrest.service.ScheduleServiceIF;
import junit.framework.TestCase;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kaiak31
 * Date: 1/29/14
 * Time: 1:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class ScheduleServiceImplTest extends TestCase{
    AbstractApplicationContext context;
    ScheduleServiceIF scheduleService;
    ActivityRepository repository;
    SlotRepository slotRepo;
    static final long ownerId = 666L;
    static final long merchantId = 90210L;
    static Activity activity;

    @Before
    public void setUp() throws Exception {
        context = new AnnotationConfigApplicationContext(Application.class);
        scheduleService = context.getBean(ScheduleServiceIF.class);
        repository = context.getBean(ActivityRepository.class);
        slotRepo = context.getBean(SlotRepository.class);
        activity = scheduleService.createActivity(merchantId,"testing",60);
        activity.setCapacity(10);
        activity = repository.save(activity);
    }



    @After
    public void tearDown() throws Exception {
           context.close();
    }

    @Test
    public void testCreateActivity(){
       Activity activity = scheduleService.createActivity(90210L, "test",15);
       assertNotNull(activity);
    }



    @Test
    public void testSlots(){
        Activity activity = new Activity(merchantId,"Test");
        repository.save(activity);
        activity = scheduleService.reattach(activity);
        List<Slot> original = new ArrayList<>();
        original.add(new Slot(activity, new DateTime()));
        original.add(new Slot(activity, new DateTime().plusHours(1)));
        original.add(new Slot(activity, new DateTime().plusHours(1)));
        original.add(new Slot(activity, new DateTime().plusDays(2)));
        slotRepo.save(original);

        List<Slot> slots = slotRepo.findByActivityId(activity.getId());
        assertTrue(!slots.isEmpty());
        DateTime start = new DateTime().withTimeAtStartOfDay();
        DateTime finish = start.plusDays(1);

        slots = slotRepo.findByActivityIdAndDateRange(activity.getId(), start.toDate(), finish.toDate());
        assertTrue((original.size()-1) == slots.size());
    }

    @Test
    public void testAddNullOwnerSlot(){
        //Adding slots
        Slot nullOwnerSlot = scheduleService.addSlot(activity.getId(), new DateTime(), null);
        assertNotNull(nullOwnerSlot);
        nullOwnerSlot = scheduleService.addSlot(activity.getId(), nullOwnerSlot.getScheduledDate(), null);
        //There is an overlapping slot of the same Activity, can't add here.
        assertNull(nullOwnerSlot);

    }

    @Test
    public void testAddOwnerSlot(){

        Slot owner = scheduleService.addSlot(activity.getId(), new DateTime(), ownerId);
        //owner.setOwnerId(ownerId);
        owner = slotRepo.save(owner);
        assertNotNull(owner);

        Slot newOwner = scheduleService.addSlot(activity.getId(), new DateTime(), ownerId);
        assertNull(newOwner);
        newOwner = scheduleService.addSlot(activity.getId(), owner.getScheduledDate(), 786L);
        assertNotNull(newOwner);
    }


    /* Checking Availability of by a Specific Day
     *
     */
    @Test
    public void testCheckAvailability(){
        DateTime startTime = new DateTime();
        Slot owner = scheduleService.addSlot(activity.getId(), startTime, ownerId);
        owner = slotRepo.save(owner);
        assertNotNull(owner);
        Slot nullOwnerSlot = scheduleService.addSlot(activity.getId(), startTime, null);
        assertNotNull(nullOwnerSlot);
        //Should return Slots that have availablitity on that day
        List<Slot> slots = scheduleService.checkAvailabilityDay(startTime,activity.getId());
        assertFalse(slots.isEmpty());
        assertTrue(slots.size()==2);

        owner = slotRepo.findOne(owner.getId());
        assertNotNull(owner);
        owner.setSpaces(0);
        slotRepo.save(owner);
        //return a list of only size 1
        slots = scheduleService.checkAvailabilityDay(startTime,activity.getId());
        assertFalse(slots.isEmpty());
        assertTrue(slots.size()==1);
        Slot test = slots.get(0);
        assertEquals(test,nullOwnerSlot);
    }

    /**
     * Testing the a slot can be booked
     */
    @Test(expected = IllegalArgumentException.class)
    public void testBookSlot(){
        DateTime startTime = new DateTime();
        Slot owner = scheduleService.addSlot(activity.getId(), startTime, ownerId);
        int originalSpaces = owner.getSpaces();
        slotRepo.save(owner);
        owner = scheduleService.bookSlot(owner.getId(),1);
        int remainingSpaces = owner.getSpaces();
        assertTrue(originalSpaces>remainingSpaces);

        //should result 0 spaces left;
        owner = scheduleService.bookSlot(owner.getId(),remainingSpaces);
        remainingSpaces = owner.getSpaces();
        assertTrue(remainingSpaces==0);
        List<Slot> slots = scheduleService.checkAvailabilityDay(owner.getScheduledDate(),activity.getId());
        assertTrue(slots.isEmpty());
        //This should return an error
        try{
            owner = scheduleService.bookSlot(owner.getId(),1);
        }catch(IllegalArgumentException ex){
            assertTrue(true);
        }


    }

}
