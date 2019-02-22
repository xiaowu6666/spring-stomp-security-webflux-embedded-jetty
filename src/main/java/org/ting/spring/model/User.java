package org.ting.spring.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;


@Data
public class User {

    private long id;

    private String username;

    private String email;

    @JsonIgnore
    private String password;

    @JsonIgnore
    private boolean enable;
    
    private String avatar;

}
