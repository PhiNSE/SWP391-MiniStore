package com.sitesquad.ministore.service.shift;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sitesquad.ministore.constant.SystemConstant;
import com.sitesquad.ministore.dto.HolidayResponse;
import com.sitesquad.ministore.model.Holiday;
import com.sitesquad.ministore.repository.HolidayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class HolidayService {

    @Autowired
    HolidayRepository holidayRepository;

    SystemConstant SystemConstant = new SystemConstant();

    @Scheduled(cron = "0 0 0 1 1 ?") // Runs on January 1st every year at midnight
    public void retrieveVietnamHolidays() {
        try {
            int year = SystemConstant.LOCAL_DATE_TIME_NOW().getYear(); // You can change the year as needed
//            year = 2024;
            List<HolidayResponse> vietnamHolidays = getVietnamHolidays(year);
            if (vietnamHolidays != null) {
                System.out.println("Retrieved Vietnam holidays for " + year);
                for (HolidayResponse holidayResponse : vietnamHolidays) {
                    System.out.println(holidayResponse.getLocalName() + " - " + holidayResponse.getDate());
                    LocalDate localDate = LocalDate.parse(holidayResponse.getDate());
                    System.out.println("**API retrieved date: " + holidayResponse.getDate());
                    if (holidayRepository.findByDate(localDate)==null){
                        Holiday saveHoliday = new Holiday();
                        saveHoliday.setDate(localDate);
                        saveHoliday.setName(holidayResponse.getLocalName());
                        holidayRepository.save(saveHoliday);
                        System.out.println(saveHoliday);
                    }
                }
            } else {
                System.out.println("Failed to retrieve Vietnam holidays for " + year);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public List<HolidayResponse> getVietnamHolidays(int year) {
        String url = "https://date.nager.at/api/v3/publicholidays/" + year + "/VN";
        RestTemplate restTemplate = new RestTemplate();
        HolidayResponse[] holidaysArray = restTemplate.getForObject(url, HolidayResponse[].class);

        return Arrays.asList(holidaysArray);
    }
}
