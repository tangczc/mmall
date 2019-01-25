package com.mmall.common;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;

/**
 * @author lichen
 */
//保证序列化json的时候，如果是null的对象，key也会消失
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ServerRespons<T> implements Serializable {
    private int status;
    private String msg;
    private T data;

    private ServerRespons(int status){
        this.status = status;
    }
    private ServerRespons(int status,T data,String msg){
        this.status = status;
        this.msg = msg;
        this.data = data;
    }
    private ServerRespons(int status,T data){
        this.status = status;
        this.data = data;
    }
    private ServerRespons(int status,String msg){
        this.status = status;
        this.msg = msg;
    }
    //使之不在json序列化结果当中
    @JsonIgnore
    public boolean isSuccess(){
        return this.status == ResponseCode.SUCCESS.getCode();
    }
    public int getStatus(){
        return status;
    }
    public T getData(){
        return data;
    }
    public String getMsg(){
        return msg;
    }

    public static <T> ServerRespons<T> createBySuccess(){
        return new ServerRespons<T>(ResponseCode.SUCCESS.getCode());
    }

    public static <T> ServerRespons<T> createBySuccessMessage(String msg){
        return new ServerRespons<>(ResponseCode.SUCCESS.getCode(),msg);
    }

    public static <T> ServerRespons<T> createBySuccess(T data){
        return new ServerRespons<>(ResponseCode.SUCCESS.getCode(),data);
    }

    public static <T> ServerRespons<T> createBySuccess(T data,String msg){
        return new ServerRespons<>(ResponseCode.SUCCESS.getCode(),data,msg);
    }

    public static <T> ServerRespons<T> createByError(){
        return new ServerRespons<>(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getDesc());
    }

    public static <T> ServerRespons<T> createByErrorMessage(String errorMessage){
        return new ServerRespons<>(ResponseCode.ERROR.getCode(),errorMessage);
    }

    public static <T> ServerRespons<T> createByErrorCodeMessage(int errorCode,String errorMessage){
        return new ServerRespons<>(errorCode,errorMessage);
    }

}
