package com.sitesquad.ministore.service;

import com.sitesquad.ministore.dto.ProductDTO;
import com.sitesquad.ministore.model.Product;
import com.sitesquad.ministore.repository.ProductRepository;
import com.sitesquad.ministore.repository.ProductTypeRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author ADMIN
 */
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductTypeRepository productTypeRepository;

    public List<ProductDTO> findAll() {
        List<Product> productList = productRepository.findByIsDeletedFalseOrIsDeletedIsNull();
        List<ProductDTO> productDTOList = new ArrayList<>();
        for (Product product : productList) {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setProductId(product.getProductId());
            productDTO.setName(product.getName());
            productDTO.setQuantity(product.getQuantity());
            productDTO.setProductTypeName(product.getProductType().getName());
            productDTO.setPrice(product.getPrice());
            productDTO.setCost(product.getCost());
            productDTO.setProductImg(product.getProductImg());
            productDTO.setProductCode(product.getProductCode());
            productDTO.setIsDeleted(product.getIsDeleted());
            productDTOList.add(productDTO);
        }
        return productDTOList;

    }

    public Page<ProductDTO> findAll(Integer offset) {
        Page<Product> productPage = productRepository.findByIsDeletedFalseOrIsDeletedIsNull(PageRequest.of(offset, 9));
        Page<ProductDTO> productDTOPage = mapDTO(productPage);
        return productDTOPage;

    }

    public Product findById(Long id) {
        Optional<Product> foundProduct = productRepository.findById(id);
        return foundProduct.get();
    }

    public Page<ProductDTO> search(Long productId, String keyword, Long productTypeId, String productCode, String sortBy, String sortType, Integer offset) {
        String name = keyword;
        String productTypeName = keyword;
        Sort sort = Sort.by(Sort.Direction.ASC,"productId");
        Sort.Direction sortDirection = Sort.Direction.ASC;
        if (sortType != null && sortType.equalsIgnoreCase("desc")) {
            sortDirection = Sort.Direction.DESC;
        }
        if (sortBy != null && sortBy.equalsIgnoreCase("price")) {
            sort = Sort.by(sortDirection, "price");
        } else if (sortBy != null && sortBy.equalsIgnoreCase("name")) {
            sort = Sort.by(sortDirection, "name");
        } else if (sortBy != null && sortBy.equalsIgnoreCase("cost")) {
            sort = Sort.by(sortDirection, "cost");
        } else if (sortBy != null && sortBy.equalsIgnoreCase("quantity")) {
            sort = Sort.by(sortDirection, "quantity");
        }
        Page<Product> productPage
                = productRepository.findByCustomQuery(productId, name, productTypeName, productTypeId, productCode, PageRequest.of(offset, 9, sort));
        Page<ProductDTO> productDTOPage = mapDTO(productPage);
        return productDTOPage;
    }

    public Page<ProductDTO> mapDTO(Page<Product> productPage) {
        Page<ProductDTO> productDTOPage = productPage.map(product -> {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setProductId(product.getProductId());
            productDTO.setName(product.getName());
            productDTO.setQuantity(product.getQuantity());
            productDTO.setProductTypeId(product.getProductTypeId());
            productDTO.setProductTypeName(product.getProductType().getName());
            productDTO.setPrice(product.getPrice());
            productDTO.setCost(product.getCost());
            productDTO.setProductImg(product.getProductImg());
            productDTO.setProductCode(product.getProductCode());
            productDTO.setIsDeleted(product.getIsDeleted());
            return productDTO;
        });
        return productDTOPage;
    }

    public Product add(Product product) {
        product.setIsDeleted(Boolean.FALSE);
        product.setProductType(productTypeRepository.findById(product.getProductTypeId()).get());
        return productRepository.save(product);
    }

    public Product edit(Product newProduct) {
        Product oldProduct = productRepository.findById(newProduct.getProductId()).get();
        newProduct.setProductId(null);
        Product editedProduct = add(newProduct);
        if (editedProduct != null) {
            oldProduct.setIsDeleted(true);
            productRepository.save(oldProduct);
        }
        return editedProduct;
    }

    public boolean delete(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return false;
        } else {
            product.setIsDeleted(true);
            productRepository.save(product);
            return true;
        }
    }
    
    public boolean minusQuantityOfProduct(Long orderQuantity, Long productId) {
        Product product = findById(productId);
        product.setQuantity(product.getQuantity() - orderQuantity);
        productRepository.save(product);
        return true;
    }
  
//Nhap hang
    
    
}
