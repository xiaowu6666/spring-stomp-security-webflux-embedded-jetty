package org.ting.spring.authentication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.ting.spring.model.Result;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.ting.spring.utils.RespnonseJson.ajaxRequest;
import static org.ting.spring.utils.RespnonseJson.jsonType;
import static org.ting.spring.utils.RespnonseJson.matchURL;

public class RestAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String uri = request.getRequestURI();
        if (matchURL(uri)){
            jsonType(response);
            String value = loginSuccess();
            response.getWriter().println(value);
        }else if (ajaxRequest(request)){
            jsonType(response);
            String success = loginSuccess();
            response.getWriter().println(success);
        }else super.onAuthenticationSuccess(request,response,authentication);
    }

    private String loginSuccess() throws JsonProcessingException {
        Result success = Result.success("sign on success go to next!");
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(success);
    }


}
