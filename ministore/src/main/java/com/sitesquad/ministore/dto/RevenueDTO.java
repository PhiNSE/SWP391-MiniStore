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
    private List<Map<String,Double>> allTimeRevenue;
    private List<List<Map<String,Object>>> monthRevenues;
    private Long totalProduct;
    private List<List<Map<String,Object>>> productRanks;
    private List<Integer> dayTimeAnalytics;
    private Long remainProductQuantity;
    private List<List<Map<String, Object>>> userRank;
}
