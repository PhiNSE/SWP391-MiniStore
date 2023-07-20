package com.sitesquad.ministore.service.shift;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sitesquad.ministore.model.Holiday;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

@Service
public class HolidayService {
//
//    @Scheduled(cron = "0 0 0 1 1 ?") // Runs on January 1st every year at midnight
//    public void retrieveVietnamHolidays() {
//        int currentYear = LocalDate.now().getYear();
//        String url = String.format("https://holidayapi.com/v1/holidays?country=VN&year=%d&key=%s", currentYear, apiKey);
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
//
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            HolidayResponse holidayResponse = objectMapper.readValue(response.getBody(), HolidayResponse.class);
//            List<Holiday> vietnamHolidays = holidayResponse.getHolidays();
//
//            if (vietnamHolidays != null) {
//                System.out.println("Retrieved Vietnam holidays for " + currentYear);
//                for (Holiday holiday : vietnamHolidays) {
//                    System.out.println(holiday.getName() + " - " + holiday.getDate());
//                }
//            } else {
//                System.out.println("Failed to retrieve Vietnam holidays for " + currentYear);
//            }
//        } catch (Exception e) {
//            System.out.println("An error occurred while retrieving Vietnam holidays: " + e.getMessage());
//        }
//    }
}
