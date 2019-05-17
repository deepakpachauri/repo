package com.pachauri.springboot.controller;

import java.util.concurrent.atomic.AtomicLong;

import com.pachauri.springboot.entity.Greeting;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private final AtomicLong counter = new AtomicLong();
    private final String template = "Hello, %s!";

    @RequestMapping(name="/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name){
        return new Greeting(counter.getAndIncrement(), String.format(name));
    }
}
