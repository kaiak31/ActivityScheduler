package com.kaibot.springrest.controllers;

import com.kaibot.springrest.entity.Activity;
import com.kaibot.springrest.entity.Slot;
import com.kaibot.springrest.service.ScheduleServiceIF;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: kaiak31
 * Date: 1/29/14
 * Time: 3:54 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class ScheduleController {
    @Autowired
    protected ScheduleServiceIF scheduleService;

    @RequestMapping("/scheduler/addActivity")
    public @ResponseBody
    Activity scheduler(
            @RequestParam(value="merchantId", required=true) Long merchantId,
            @RequestParam(value="capacity", required=false) Integer capacity,
            @RequestParam(value="description")String description)
            {
            if(merchantId == null ||  description == null){
                  throw new IllegalArgumentException();
            }
                Activity activity;
         if(capacity == null || capacity.intValue() <= 0){
            activity = scheduleService.createActivity(merchantId, description,0);
         }
         else{
            activity = scheduleService.createActivity(merchantId,description,capacity);
         }
         return activity;
     }

    @RequestMapping("/scheduler/addSlot")
    public @ResponseBody Slot addSlot(
            @RequestParam(value="activityId") Long activityId,
            @RequestParam(value="date") @DateTimeFormat(pattern="MM/dd/yyyy:hh:mm:ss") Date date,
            @RequestParam(value="ownerId", required = false) Long ownerId){
           if(activityId==null && date==null){
               throw new IllegalArgumentException();
           }
           Slot slot  = scheduleService.addSlot(activityId, new DateTime(date), ownerId);
           return slot;
       }



}
