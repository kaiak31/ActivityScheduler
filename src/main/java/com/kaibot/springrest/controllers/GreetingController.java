/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kaibot.springrest.controllers;

import com.kaibot.springrest.entity.Greeting;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author kaiak31
 */
@Controller
public class GreetingController {
     private static final String template = "Hello, %s!";
     private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public @ResponseBody Greeting greeting(
            @RequestParam(value="name", required=false, defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));
    }


}
