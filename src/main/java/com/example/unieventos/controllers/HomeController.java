
package com.example.unieventos.controllers;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class HomeController {


    @GetMapping("/test")
    public String test() {
        return "Backend de UniEventos funcionando";
    }
}
