package com.sitesquad.ministore.controller.admin;

import com.sitesquad.ministore.dto.*;
import com.sitesquad.ministore.model.Order;
import com.sitesquad.ministore.model.OrderDetails;
import com.sitesquad.ministore.model.Payslip;
import com.sitesquad.ministore.model.User;
import com.sitesquad.ministore.service.*;
import com.sitesquad.ministore.service.shift.UserShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
    UserShiftService userShiftService;
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
            if (order.getTotal() != null) {
                if (order.getType() == false) {
                    revenue += order.getTotal();
                }
                if (order.getType() == true) {
                    expense += order.getTotal();
                }
            }
        }

        List<Payslip> payslipList = payslipService.findAll();
        for (Payslip payslip : payslipList) {
            expense += payslip.getSalary();
        }

        revenueMap.put("revenue", revenue);
        profitMap.put("profit", revenue - expense);
        allTimeRevenue.add(revenueMap);
        allTimeRevenue.add(profitMap);
        return allTimeRevenue;
    }

    private List<Map<String, Object>> monthRevenue() {
        List<Map<String, Object>> monthRevenuesList = new ArrayList<>();
        Map<String, Object> monthRevenueMap = new HashMap<>();
        List<Object> monthRevenue = new ArrayList<>();
        List<Object> monthCost = new ArrayList<>();

        for (int i = 1; i <= 12; i++) {
            Double revenue = new Double(0);
            Double cost = new Double(0);
            List<Order> orderList = orderService.findByDate(i);
            if (!orderList.isEmpty()) {
                for (Order order : orderList) {
                    if (order.getTotal() != null) {
                        if (order.getType() == false) {
                            revenue += order.getTotal();
                        }
                        if (order.getType() == true) {
                            cost += order.getTotal();
                        }
                    }
                }
                List<Payslip> payslipList = payslipService.findPayslipByMonth(i);
                for (Payslip payslip : payslipList) {
                    cost += payslip.getSalary();
                }
            }
            monthRevenue.add(revenue);
            monthCost.add(cost);
        }
        monthRevenueMap.put("revenue", monthRevenue);
        monthRevenueMap.put("cost", monthCost);
        monthRevenuesList.add(monthRevenueMap);
        return monthRevenuesList;
    }

    private Long totalProduct() {
        Long totalProduct = new Long(0);
        List<OrderDetails> orderDetailsList = orderDetailsService.findAll();

        for (OrderDetails orderDetail : orderDetailsList) {
            if (orderDetail.getOrderDet().getType() == false) {
                totalProduct += orderDetail.getQuantity();
            }
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
            if (productDTO.getIsDeleted() == null || productDTO.getIsDeleted() == false) {
                remainProductQuantity += productDTO.getQuantity();
            }
        }
        return remainProductQuantity;
    }

    private List<List<Map<String, Object>>> userRank() {
        List<List<Map<String, Object>>> userRankList = new ArrayList<>();
        List<Map<String, Object>> shiftList = userShiftService.findByUserRank();
        List<Map<String, Object>> filteredShiftList = new ArrayList<>();

        for (Map<String, Object> shift : shiftList) {
            Integer userId = (Integer) shift.get("user_id");
            User user = userService.find(userId.longValue());
            if (user != null) {
                filteredShiftList.add(shift);
            }
        }

        for (Map<String, Object> shift : filteredShiftList) {
            List<Map<String, Object>> userRankMap = new ArrayList<>();
            Map<String, Object> userMap = new HashMap<>();
            Map<String, Object> totalMoneyMap = new HashMap<>();
            Integer userId = (Integer) shift.get("user_id");
            Integer totalShiftCount = (Integer) shift.get("total_shift_count");

            User user = userService.find(userId.longValue());
            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(user.getUserId());
            userDTO.setName(user.getName());
            userDTO.setEmail(user.getEmail());
            userDTO.setPhone(user.getPhone());
            userDTO.setAddress(user.getAddress());
            userDTO.setDob(user.getDob());
            userDTO.setRoles(user.getRole().getName());
            userDTO.setGender(user.getGender());
            userDTO.setUserImg(user.getUserImg());
            userDTO.setRfid(user.getRfid());
            userMap.put("user", userDTO);
            totalMoneyMap.put("totalShiftCount", totalShiftCount);
            userRankMap.add(userMap);
            userRankMap.add(totalMoneyMap);
            userRankList.add(userRankMap);
        }

        return userRankList;
    }
}
