package org.ting.test;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.ting.spring.model.Result;

import java.util.regex.Pattern;

public class PasswdEncoding {

    @Test
    public void passwd(){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode("123456");
        System.out.println("passwd:"+ encode);
    }

    @Test
    public void obj2json() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Result result = Result.error(302, "我要执行崇兴乡");
        String s = mapper.writeValueAsString(result);
        System.out.println(s);
    }


}
