package com.sitesquad.ministore.controller.admin;

import com.sitesquad.ministore.dto.ProductDTO;
import com.sitesquad.ministore.dto.RequestMeta;
import com.sitesquad.ministore.dto.ResponseObject;
import com.sitesquad.ministore.dto.RevenueDTO;
import com.sitesquad.ministore.model.Order;
import com.sitesquad.ministore.model.OrderDetails;
import com.sitesquad.ministore.model.Product;
import com.sitesquad.ministore.service.OrderDetailsService;
import com.sitesquad.ministore.service.OrderService;
import com.sitesquad.ministore.service.PayslipService;
import com.sitesquad.ministore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
    RequestMeta requestMeta;

    @GetMapping("/dashboard")
    public ResponseEntity<ResponseObject> dashboard() {
        if(!requestMeta.getRole().equals("admin")) {
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
        System.out.println(revenueDTO);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "Successful", revenueDTO)
        );
    }

    private Map<Double, Double> allTimeRevenue() {
        Map<Double, Double> allTimeRevenue = new HashMap<>();

        Double revenue = new Double(0);
        Double expense = new Double(0);

        List<Order> orderList = orderService.findAll();
        for (Order order : orderList) {
            if (order.getType() == false) {
                revenue += order.getTotal();
            }
            if (order.getType() == true) {
                expense += order.getTotal();
            }
        }
        allTimeRevenue.put(revenue, revenue - expense);
        return allTimeRevenue;
    }

    private List<Map<YearMonth, Double>> monthRevenue() {
        List<Map<YearMonth, Double>> monthRevenuesList = new ArrayList<>();

        YearMonth month = YearMonth.of(2023, 6); //fix cung
        for (int i = 0; i <= 11; i++) {
            Map<YearMonth, Double> yearMonthDoubleMap = new HashMap<>();
            Double revenue = new Double(0);
            List<Order> orderList = orderService.findByDate(month);
            if (orderList.isEmpty()) {
                break;
            }
            for (Order order : orderList) {
                if (order.getType() == false) {
                    revenue += order.getTotal();
                }
                if (order.getType() == true) {
                    revenue -= order.getTotal();
                }
            }
            yearMonthDoubleMap.put(month, revenue);
            monthRevenuesList.add(yearMonthDoubleMap);
            month = month.plus(Period.ofMonths(1));
        }
        return monthRevenuesList;
    }

    private Long totalProduct() {
        Long totalProduct = new Long(0);
        List<OrderDetails> orderDetailsList = orderDetailsService.findAll();

        for(OrderDetails orderDetail: orderDetailsList) {
            totalProduct += orderDetail.getQuantity();
        }

        return totalProduct;
    }

    private List<Map<Integer, Integer>> productRanks() {
        List<Map<Integer, Integer>> productRankList = new ArrayList<>();

        List<Map<String, Object>> orderDetailsList = orderDetailsService.findTotalQuantityOfProduct();
        System.out.println(orderDetailsList);

        for(Map<String, Object> orderDetails: orderDetailsList) {
            Map<Integer, Integer> productRank = new HashMap<>();
            Integer productId = (Integer) orderDetails.get("product_id");
            Integer sumQuantity = (Integer) orderDetails.get("sum_quantity");
            productRank.put(productId, sumQuantity);
            productRankList.add(productRank);
        }

        return productRankList;
    }

    private List<Map<Integer,Integer>> dayTimeAnalytics() {
        List<Map<Integer, Integer>> mostSoldHourList = new ArrayList<>();

        List<Map<String, Object>> orderList = orderService.findMostSoldHour();

        for(Map<String, Object> order: orderList) {
            Map<Integer, Integer> mostSoldHour = new HashMap<>();
            Integer hour = (Integer) order.get("hour_in_day");
            Integer count = (Integer) order.get("number_of_order");
            mostSoldHour.put(hour, count);
            mostSoldHourList.add(mostSoldHour);
        }

        return mostSoldHourList;
    }

    private Long remainProductQuantity() {
        Long remainProductQuantity = new Long(0);
        List<ProductDTO> productList = productService.findAll();
        for(ProductDTO productDTO: productList) {
            remainProductQuantity += productDTO.getQuantity();
        }
        return remainProductQuantity;
    }
}
