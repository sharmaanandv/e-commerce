package com.ecomm.comms.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {

    private long id;
    private String username;
    private String roles;

}