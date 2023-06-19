package com.sitesquad.ministore.dto;

import com.sitesquad.ministore.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RevenueDTO {
    private Double allTimeRevenue;
    private Map<YearMonth,Double> mothRevenues;
    private Integer totalProduct;
    private List<Map<Product,Integer>> productRanks;
    private List<Map<LocalDate,Integer>> dayTimeAnalytics;
    private Long remainProductQuantity;

}
