package com.sitesquad.ministore;

import com.sitesquad.ministore.model.Product;
import com.sitesquad.ministore.repository.ProductRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller  // Chỉ định HomeController là Controller
public class HomeController {
    @Autowired
    ProductRepository productRepository;
    // Khi user truy cập vào endpoint / thì homepage() được gọi
    @GetMapping("/")
    public String homepage() {
//        for(Account p : accs){
//            System.out.println(p.toString());
//        }
        List<Product> products = productRepository.findAll();
        for (Product product : products) {
            System.out.println(product);
        }
        return "index";  // Trả về trang index.html
    }

    // Có thể mapping thêm các endpoint khác nữa...
    
    
   
}
