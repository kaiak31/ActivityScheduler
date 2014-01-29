package com.kaibot.springrest.test;


import com.kaibot.springrest.Application;
import com.kaibot.springrest.entity.Activity;
import com.kaibot.springrest.entity.Slot;
import com.kaibot.springrest.repository.ActivityRepository;
import com.kaibot.springrest.repository.SlotRepository;
import com.kaibot.springrest.service.ScheduleServiceIF;
import junit.extensions.TestSetup;
import junit.framework.TestCase;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import javax.naming.Context;
import java.util.List;

public class TestApplication extends TestCase {
    AbstractApplicationContext context;
    ScheduleServiceIF scheduleService;
    ActivityRepository repository;
    SlotRepository slotRepo;

    @Before
    public void setUp(){
       context = new AnnotationConfigApplicationContext(Application.class);
       scheduleService = context.getBean(ScheduleServiceIF.class);
       repository = context.getBean(ActivityRepository.class);
       slotRepo = context.getBean(SlotRepository.class);
    }

    @Test
    public void testApp(){
        Activity a = new Activity(90210l, "Test");
        repository.save(a);
        List<Activity> list = scheduleService.findAllActivities();
        assertTrue(false);
        for(Activity activity: list){
            activity = scheduleService.reattach(activity);
            slotRepo.findAll();
            System.out.println("THis is the slot "+activity);
        }
    }

    @Test
    public void testSlots(){

    }
}