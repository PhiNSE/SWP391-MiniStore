package com.sitesquad.ministore.service;

import com.sitesquad.ministore.model.ProductVoucher;
import com.sitesquad.ministore.repository.ProductRepository;
import com.sitesquad.ministore.repository.ProductVoucherRepository;
import com.sitesquad.ministore.repository.VoucherRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author admin
 */
@Service
public class ProductVoucherService {

    @Autowired
    ProductVoucherRepository productVoucherRepository;
    
    @Autowired
    VoucherRepository voucherRepository;
    
    @Autowired
    ProductRepository productRepository;

    public List<ProductVoucher> findAll() {
        return productVoucherRepository.findAll();
    }

    public ProductVoucher findById(Long id) {
        Optional<ProductVoucher> foundProductVoucher = productVoucherRepository.findById(id);
        return foundProductVoucher.get();
    }
    
//    public List<ProductVoucher> findByProductId(Long id) {
//        Optional<ProductVoucher> foundProductVoucher = productVoucherRepository.findById(id);
//        return foundProductVoucher.get();
//    }

    public ProductVoucher add(ProductVoucher productVoucher) {
        productVoucher.setProduct(productRepository.findById(productVoucher.getProductId()).get());
        productVoucher.setVoucher(voucherRepository.findById(productVoucher.getVoucherId()).get());
        return productVoucherRepository.save(productVoucher);
    }
}
