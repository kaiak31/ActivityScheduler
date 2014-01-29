package com.kaibot.springrest.test;

import com.kaibot.springrest.Application;
import com.kaibot.springrest.entity.Activity;
import com.kaibot.springrest.entity.Slot;
import com.kaibot.springrest.repository.ActivityRepository;
import com.kaibot.springrest.repository.SlotRepository;
import com.kaibot.springrest.service.ScheduleServiceIF;
import junit.framework.TestCase;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import javax.transaction.Transactional;
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

    @Before
    public void setUp() throws Exception {
        context = new AnnotationConfigApplicationContext(Application.class);
        scheduleService = context.getBean(ScheduleServiceIF.class);
        repository = context.getBean(ActivityRepository.class);
        slotRepo = context.getBean(SlotRepository.class);
    }



    @After
    public void tearDown() throws Exception {
           context.close();
    }



    @Test
    public void testSlots(){
        Activity activity = new Activity(90210L,"Test");
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
    public void testDateRangeAvailability(){


    }
}
