package com.sitesquad.ministore.service;

import com.sitesquad.ministore.model.Product;
import com.sitesquad.ministore.model.ProductVoucher;
import com.sitesquad.ministore.repository.ProductRepository;
import com.sitesquad.ministore.repository.ProductVoucherRepository;
import com.sitesquad.ministore.repository.VoucherRepository;

import java.util.ArrayList;
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
        return productVoucherRepository.findByIsDeletedNullOrIsDeletedFalse();
    }

    public ProductVoucher findById(Long id) {
        Optional<ProductVoucher> foundProductVoucher = productVoucherRepository.findById(id);
        return foundProductVoucher.get();
    }
    
    public List<ProductVoucher> findByProductId(Long id) {
        List<ProductVoucher> productVoucherList = productVoucherRepository.findByIsDeletedNullOrIsDeletedFalse();
        List<ProductVoucher> foundProductVoucher = new ArrayList<>();
        for(ProductVoucher productVoucher: productVoucherList) {
            if(productVoucher.getProductId().equals(id)) {
                foundProductVoucher.add(productVoucher);
            }
        }

        return foundProductVoucher;
    }
    
    public ProductVoucher findByVoucherIdAndProductId(Long voucherId, Long productId) {
//        ProductVoucher foundProductVoucher = productVoucherRepository.findByVoucherIdAndProductId(voucherId, productId);
        List<ProductVoucher> productVoucherList = productVoucherRepository.findByIsDeletedNullOrIsDeletedFalse();
        ProductVoucher foundProductVoucher = new ProductVoucher();
        for(ProductVoucher productVoucher: productVoucherList) {
            if(productVoucher.getVoucherId().equals(voucherId) && productVoucher.getProductId().equals(productId)) {
                foundProductVoucher = productVoucher;
            }
        }
        return foundProductVoucher;
    }

    public ProductVoucher add(ProductVoucher productVoucher) {
        productVoucher.setProduct(productRepository.findById(productVoucher.getProductId()).get());
        productVoucher.setVoucher(voucherRepository.findById(productVoucher.getVoucherId()).get());
        return productVoucherRepository.save(productVoucher);
    }

    public ProductVoucher edit(ProductVoucher productVoucher) {
        return productVoucherRepository.save(productVoucher);
    }
}
