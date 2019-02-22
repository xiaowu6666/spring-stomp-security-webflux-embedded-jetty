package org.ting.spring.model;


import lombok.Data;
@Data
public class Result {

    public final static int HTTP_OK = 200;

    public final static int HTTP_SERVER_ERR = 500;

    public final static int HTTP_AUTH_FAILURE = 401;

    public final static int HTTP_FORBIDDEN = 403;

    private int code;

    private String description;

    private Object details;

    public static Result success(){
        Result result = new Result();
        result.code = HTTP_OK;
        result.details = "success";
        return result;
    }

    public static Result success(Object details){
        Result result = new Result();
        result.code = HTTP_OK;
        result.details = details;
        return result;
    }

    public static Result error(String description){
        Result result = new Result();
        result.code = HTTP_SERVER_ERR;
        result.description = description;
        return result;
    }

    public static Result error(int code,String description){
        Result result = new Result();
        result.code = code;
        result.description = description;
        return result;
    }
}
