package com.sitesquad.ministore;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller  
public class HomeController {

    @GetMapping("/")
    public String homepage() {

        
        return "index";  // Trả về trang index.html
    }     
   
}
