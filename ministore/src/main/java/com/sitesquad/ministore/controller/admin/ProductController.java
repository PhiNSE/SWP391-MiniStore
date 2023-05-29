package com.sitesquad.ministore.controller.admin;

import com.sitesquad.ministore.model.Product;
import com.sitesquad.ministore.model.ResponseObject;
import com.sitesquad.ministore.repository.ProductRepository;
import com.sitesquad.ministore.repository.ProductTypeRepository;
import com.sitesquad.ministore.service.ProductService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ADMIN
 */
@RestController
public class ProductController {

    @Autowired
    ProductService productService;


//    @GetMapping("/product")
//    public ResponseEntity<ResponseObject> getProducts() {
//        List<Product> products = productService.findAll();
//        if (products != null && !products.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.OK).body(
//                    new ResponseObject(200, "Product list", products)
//            );
//        } else {
//            return ResponseEntity.status(HttpStatus.OK).body(
//                    new ResponseObject(404, "Empty product list", "")
//            );
//        }
//    }

    @GetMapping("/product")
    public ResponseEntity<ResponseObject> getProducts(@RequestParam(required = false) Integer offset) {
        if (offset==null) {
            offset = 1;
        }
        Page<Product> productList = productService.findAll(offset);
        if (productList != null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Found Product List", productList)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(500, "Cant find product list", "")
            );
        }

    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ResponseObject> findById(@PathVariable Long id) {
        Product foundProduct = productService.findById(id);
        if (foundProduct != null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Found Product id = " + id, foundProduct)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(500, "Cant find Product id = " + id, "")
            );
        }
    }

    @GetMapping("/product/search/{offset}")
    public ResponseEntity<ResponseObject> search(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long productTypeId,
            @RequestParam(required = false) String productCode,
            @RequestParam(required = false) String priceSort) {
        List<Product> foundProducts = productService.search( id, keyword, productTypeId, productCode, priceSort);
        if (foundProducts != null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Found Products ", foundProducts)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(404, "Cant find any Products matched", "")
            );
        }
    }

    @PostMapping("/product")
    public ResponseEntity<ResponseObject> addProduct(@RequestBody Product product) {
        Product addProduct = productService.add(product);
        if (addProduct != null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Add sucessfully ", addProduct)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(500, "Cant add product", product)
            );
        }

    }

    @PutMapping("/product")
    public ResponseEntity<ResponseObject> editProduct(@RequestBody Product product) {
        Product editedProduct = productService.edit(product);
        if (editedProduct != null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Edit sucessfully ", editedProduct)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(500, "Cant edit product", product)
            );
        }
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<ResponseObject> deleteProduct(@PathVariable Long id) {
        Boolean isDeleted = productService.delete(id);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Delete sucessfully ", "")
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(500, "Cant delete product", "")
            );
        }
    }
}
