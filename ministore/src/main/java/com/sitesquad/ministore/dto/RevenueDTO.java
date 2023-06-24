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
    private Map<Double,Double> allTimeRevenue;
    private List<Map<YearMonth,Double>> monthRevenues;
    private Long totalProduct;
    private List<Map<Integer,Integer>> productRanks;
    private List<Map<Integer,Integer>> dayTimeAnalytics;
    private Long remainProductQuantity;

}
