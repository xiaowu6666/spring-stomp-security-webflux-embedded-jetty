package org.ting.spring.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;
import org.ting.spring.model.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Pattern;

public class RespnonseJson {

    public static void jsonType(HttpServletResponse response) {
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
    }

    public static boolean ajaxRequest(HttpServletRequest request){
        String header = request.getHeader("X-Requested-With");
        return ! StringUtils.isEmpty(header) && header.equals("XMLHttpRequest");
    }

    public static boolean matchURL(String url) {
        Pattern compile = Pattern.compile("^/api/.+");
        return compile.matcher(url).matches();
    }
}
