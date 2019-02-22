package org.ting.spring.authentication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.ting.spring.model.Result;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.ting.spring.utils.RespnonseJson.*;

public class RestAuthenticationEntryPoint  extends LoginUrlAuthenticationEntryPoint {

    /**
     * @param loginFormUrl URL where the login page can be found. Should either be
     *                     relative to the web-app context path (include a leading {@code /}) or an absolute
     *                     URL.
     */
    public RestAuthenticationEntryPoint(String loginFormUrl) {
        super(loginFormUrl);
    }


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String uri = request.getRequestURI();
        if (matchURL(uri)) {
            jsonType(response);
            response.getWriter().println(getErr(authException.getMessage()));
        }else if (ajaxRequest(request)){
            jsonType(response);
            response.getWriter().println(getErr(authException.getMessage()));
        }else super.commence(request,response,authException);
    }

    private String getErr(String description) throws JsonProcessingException {
        Result result = Result.error(Result.HTTP_FORBIDDEN, description);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(result);
    }

}
