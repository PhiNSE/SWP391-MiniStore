package com.sitesquad.ministore.controller.admin;

import com.sitesquad.ministore.dto.ProductDTO;
import com.sitesquad.ministore.dto.RequestMeta;
import com.sitesquad.ministore.dto.ResponseObject;
import com.sitesquad.ministore.dto.RevenueDTO;
import com.sitesquad.ministore.model.Order;
import com.sitesquad.ministore.model.OrderDetails;
import com.sitesquad.ministore.model.Product;
import com.sitesquad.ministore.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.net.Inet4Address;
import java.time.Period;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class RevenueDashboard {
    @Autowired
    OrderService orderService;
    @Autowired
    OrderDetailsService orderDetailsService;
    @Autowired
    ProductService productService;
    @Autowired
    PayslipService payslipService;
    @Autowired
    UserService userService;
    @Autowired
    RequestMeta requestMeta;

    @GetMapping("/dashboard")
    public ResponseEntity<ResponseObject> dashboard() {
        if (!requestMeta.getRole().equals("admin")) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(404, "You don't have permission", "")
            );
        }
        RevenueDTO revenueDTO = new RevenueDTO();
        revenueDTO.setAllTimeRevenue(allTimeRevenue());
        revenueDTO.setMonthRevenues(monthRevenue());
        revenueDTO.setTotalProduct(totalProduct());
        revenueDTO.setProductRanks(productRanks());
        revenueDTO.setDayTimeAnalytics(dayTimeAnalytics());
        revenueDTO.setRemainProductQuantity(remainProductQuantity());
        revenueDTO.setUserRank(userRank());
        System.out.println(revenueDTO);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "Successful", revenueDTO)
        );
    }

    private List<Map<String, Double>> allTimeRevenue() {
        List<Map<String, Double>> allTimeRevenue = new ArrayList<>();

        Double revenue = new Double(0);
        Double expense = new Double(0);
        Map<String, Double> revenueMap = new HashMap<>();
        Map<String, Double> profitMap = new HashMap<>();

        List<Order> orderList = orderService.findAll();
        for (Order order : orderList) {
            if (order.getType() == false) {
                revenue += order.getTotal();
            }
            if (order.getType() == true) {
                expense += order.getTotal();
            }
        }
        revenueMap.put("revenue", revenue);
        profitMap.put("profit", revenue - expense);
        allTimeRevenue.add(revenueMap);
        allTimeRevenue.add(profitMap);
        return allTimeRevenue;
    }

    private List<List<Map<String, Object>>> monthRevenue() {
        List<List<Map<String, Object>>> monthRevenuesList = new ArrayList<>();

//        YearMonth month = YearMonth.of(2023, 6); //fix cung
        for (int i = 1; i <= 12; i++) {
            List<Map<String, Object>> monthRevenueMap = new ArrayList<>();
            Map<String, Object> monthMap = new HashMap<>();
            Map<String, Object> revenueMap = new HashMap<>();
            Double revenue = new Double(0);
            List<Order> orderList = orderService.findByDate(i);
            if (!orderList.isEmpty()) {
                for (Order order : orderList) {
                    if (order.getType() == false) {
                        revenue += order.getTotal();
                    }
                }
            }
            monthMap.put("month", i);
            revenueMap.put("revenue", revenue);
            monthRevenueMap.add(monthMap);
            monthRevenueMap.add(revenueMap);
            monthRevenuesList.add(monthRevenueMap);

        }
        return monthRevenuesList;
    }

    private Long totalProduct() {
        Long totalProduct = new Long(0);
        List<OrderDetails> orderDetailsList = orderDetailsService.findAll();

        for (OrderDetails orderDetail : orderDetailsList) {
            totalProduct += orderDetail.getQuantity();
        }

        return totalProduct;
    }

    private List<List<Map<String, Object>>> productRanks() {
        List<List<Map<String, Object>>> productRankList = new ArrayList<>();

        List<Map<String, Object>> orderDetailsList = orderDetailsService.findTotalQuantityOfProduct();

        for (Map<String, Object> orderDetails : orderDetailsList) {
            List<Map<String, Object>> productRankMap = new ArrayList<>();
            Map<String, Object> productIdMap = new HashMap<>();
            Map<String, Object> sumQuantityMap = new HashMap<>();
            Integer productId = (Integer) orderDetails.get("product_id");
            Integer sumQuantity = (Integer) orderDetails.get("sum_quantity");

            productIdMap.put("product", productService.findById(productId.longValue()));
            sumQuantityMap.put("sumQuantity", sumQuantity);
            productRankMap.add(productIdMap);
            productRankMap.add(sumQuantityMap);
            productRankList.add(productRankMap);
        }

        return productRankList;
    }

    private List<Integer> dayTimeAnalytics() {
        List<Integer> mostSoldHourList = new ArrayList<>();
        List<Map<String, Object>> orderList = orderService.findMostSoldHour();

        for (Integer i = 1; i <= 24; i++) {
            for (Map<String, Object> order : orderList) {
                Integer hour = (Integer) order.get("hour_in_day");
                Integer count = (Integer) order.get("number_of_order");
                if (i.equals(hour)) {
                    mostSoldHourList.add(count);
                    i++;
                }
            }
            mostSoldHourList.add(0);
        }

        return mostSoldHourList;
    }

    private Long remainProductQuantity() {
        Long remainProductQuantity = new Long(0);
        List<ProductDTO> productList = productService.findAll();
        for (ProductDTO productDTO : productList) {
            remainProductQuantity += productDTO.getQuantity();
        }
        return remainProductQuantity;
    }

    private List<List<Map<String, Object>>> userRank() {
        List<List<Map<String, Object>>> userRankList = new ArrayList<>();
        List<Map<String, Object>> orderList = orderService.findByUserRank();

        for (Map<String, Object> order : orderList) {
            List<Map<String, Object>> userRankMap = new ArrayList<>();
            Map<String, Object> userMap = new HashMap<>();
            Map<String, Object> totalMoneyMap = new HashMap<>();
            Integer user = (Integer) order.get("user_id");
            BigDecimal totalMoney = (BigDecimal) order.get("total_money");

            userMap.put("user", userService.find(user.longValue()));
            totalMoneyMap.put("totalRevenue", Double.valueOf(totalMoney.doubleValue()));
            userRankMap.add(userMap);
            userRankMap.add(totalMoneyMap);
            userRankList.add(userRankMap);
        }

        return userRankList;
    }
}
