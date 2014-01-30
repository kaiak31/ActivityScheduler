package com.kaibot.springrest.controllers;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.kaibot.springrest.entity.Activity;
import com.kaibot.springrest.service.ScheduleServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;

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
            @RequestParam(value="merchantName", required=true) Long merchantId,
            @RequestParam(value="description")String description){
            if(merchantId == null ||  description == null){
                  throw new IllegalArgumentException();
            }

    }
}
