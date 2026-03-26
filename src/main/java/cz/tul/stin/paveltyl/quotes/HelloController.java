package cz.tul.stin.paveltyl.quotes;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello() {
        return "Hello REST, Spring Boot, Lombok, Maven!";
    }
}
