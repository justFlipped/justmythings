package com.springboot.controller;

import com.springboot.Bean.Person;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class springbootController {
        @Value("${person.name}")
        private String name;

        @RequestMapping("/hello")
        public String hello(){
                return "Hello World"+name;
        }

}
