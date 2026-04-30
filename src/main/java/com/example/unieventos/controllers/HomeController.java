
package com.example.unieventos.controllers;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", allowedHeaders = "*") //permitir conexion de diferentes puertos
public class HomeController {


    @GetMapping("/test")
    public String test() {
        return "{\n" +
                "  \"status\": \"success\",\n" +
                "  \"request\": \"Backend unieventos funcionado\",\n" +
                "  \"timestamp\": \" "+ LocalDateTime.now() +"  \",\n" +
                "  \"data\": {},\n" +
                "  \"meta\": {\n" +
                "    \"version\": \"1.0\"\n" +
                "  }\n" +
                "}";
    }
}
