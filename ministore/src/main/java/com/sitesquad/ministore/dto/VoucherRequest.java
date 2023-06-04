package com.sitesquad.ministore.dto;

import com.sitesquad.ministore.model.Product;
import com.sitesquad.ministore.model.Voucher;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author admin
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoucherRequest implements Serializable {
    private List<Product> productList;
    private Voucher voucher;
}
