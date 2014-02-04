package com.kaibot.springrest.controllers;

import com.kaibot.springrest.ErrorMessage;
import com.kaibot.springrest.entity.Activity;
import com.kaibot.springrest.entity.Slot;
import com.kaibot.springrest.service.ScheduleServiceIF;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

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
    Activity addActivity(
            @RequestParam(value="merchantId", required=true) Long merchantId,
            @RequestParam(value="duration", required=false) Integer duration,
            @RequestParam(value="description")String description)
            {
            if(merchantId == null ||  description == null){
                  throw new IllegalArgumentException();
            }
            Activity activity = scheduleService.createActivity(merchantId,description,duration);

         return activity;
     }

    /**
     * Separate functionality from addActivity, should require validation
     * @param activity
     * @return
     */
    @RequestMapping("/scheduler/editActivity")
    public @ResponseBody Activity editActivity( @ModelAttribute("activity")Activity activity)
    {
        if(activity == null){
            throw new IllegalArgumentException();
        }
        Activity newActivity= scheduleService.saveOrUpdateActivity(activity);
        return newActivity;
    }


    @RequestMapping("/scheduler/addSlot")
    public @ResponseBody Slot addSlot(
            @RequestParam(value="activityId") Long activityId,
            @RequestParam(value="date") @DateTimeFormat(pattern="MM/dd/yyyy:hh:mm:ss") Date date,
            @RequestParam(value="ownerId", required = false) Long ownerId){
           if(activityId==null || date==null){
               throw new IllegalArgumentException();
           }
           Slot slot  = scheduleService.addSlot(activityId, new DateTime(date), ownerId);
           return slot;
       }

    @RequestMapping("/scheduler/checkAvailability")
    public @ResponseBody List<Slot> checkAvailability(
            @RequestParam(value="activityId") Long activityId,
            @RequestParam(value="date") @DateTimeFormat(pattern="MM/dd/yyyy:hh:mm:ss") Date date)
            {
            if(activityId==null && date==null){
                throw new IllegalArgumentException();
            }
        List<Slot> slots = scheduleService.checkAvailabilityDay(new DateTime(date), activityId);
        return slots;
    }

    @RequestMapping("/scheduler/checkAvailabilityDateRange")
    public @ResponseBody List<Slot> checkAvailabilityDateRange(
            @RequestParam(value="activityId") Long activityId,
            @RequestParam(value="start") @DateTimeFormat(pattern="MM/dd/yyyy:hh:mm:ss") Date start,
            @RequestParam(value="end") @DateTimeFormat(pattern="MM/dd/yyyy:hh:mm:ss") Date end
            )
    {
        if(activityId==null || start==null || end==null){
            throw new IllegalArgumentException();
        }
        List<Slot> slots = scheduleService.checkAvailabilityByDateRange(activityId,new DateTime(start),new DateTime(end));
        return slots;
    }

    @RequestMapping("/scheduler/editSlot")
    public @ResponseBody Slot editSlot(
        @RequestParam Long slotId,
        @RequestParam Long ownerId,
        @RequestParam Integer duration,
        @RequestParam Integer spaces
    ){
      if(slotId == null || duration == null || spaces == null ){
              throw new IllegalArgumentException("Improper arguments");
        }
        return scheduleService.editSlot(slotId,ownerId,duration,spaces);
    }

    @RequestMapping("/scheduler/bookSlot")
    public @ResponseBody Slot bookSlot(
            @RequestParam(value="slotId") Long slotId,
            @RequestParam(value = "spaces") int spaces
    )
    {
        if(slotId==null || spaces<=0 ){
            throw new IllegalArgumentException("Improper arguments");
        }
        Slot slot = scheduleService.bookSlot(slotId, spaces);
        return slot;
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public @ResponseBody
    String handleException(Exception e, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return e.getMessage();
    }

}
