package com.online.market.authentificationservice.entries;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserVO {

    private String email;
    private String password;
    private String role;

}
