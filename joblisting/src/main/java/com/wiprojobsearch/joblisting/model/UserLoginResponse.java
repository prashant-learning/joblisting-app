package com.wiprojobsearch.joblisting.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginResponse {


    private String userId;
    private String username;
    private String email;
    private String phoneNumber;
}
