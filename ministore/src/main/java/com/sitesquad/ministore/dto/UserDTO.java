package com.sitesquad.ministore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long userId;

    private String name;

    private String email;

    private String phone;

    private String address;

    private String roles;

    private Date dob;

    private boolean gender;

    private String userImg;

    private String rfid;

}
