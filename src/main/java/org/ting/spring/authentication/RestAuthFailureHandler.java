package org.ting.spring.authentication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.ting.spring.model.Result;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.ting.spring.utils.RespnonseJson.*;

public class RestAuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if (ajaxRequest(request)){
            jsonType(response);
            String err = getErr(exception.getMessage());
            response.getWriter().println(err);
        }else super.onAuthenticationFailure(request,response,exception);
    }

    public String getErr(String description) throws JsonProcessingException {
        Result result = Result.error(Result.HTTP_AUTH_FAILURE, description);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(result);
    }
}
