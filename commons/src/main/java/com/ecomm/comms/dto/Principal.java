package com.ecomm.comms.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Principal {

    private long id;
    private String username;
    private List<String> roles;
    private String token;

}