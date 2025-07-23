package br.edu.ifsp.spo.java.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //Indicando que a classe é um controller rest
@RequestMapping("/hello-world")
public class HelloWorldController {

    @GetMapping
    public String helloWorld(){
        return "Hello World";
    }
}
