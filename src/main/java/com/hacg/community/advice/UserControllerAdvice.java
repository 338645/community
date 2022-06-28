package com.hacg.community.advice;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * 统一异常处理
 */
@RestControllerAdvice
public class UserControllerAdvice {
    //处理直接在参数上加@RequestParam或参数加@NotBlank @NotNull等错误
    @ExceptionHandler(ConstraintViolationException.class)
    public String exConstructorEr(ConstraintViolationException e) {
        return e.getMessage();
    }

    //处理表单提交验证失败
    @ExceptionHandler(BindException.class)
    public String exBindEr(BindException e) {
        StringBuilder sb = new StringBuilder();
        List<FieldError> fieldErrors = e.getFieldErrors();
        for (FieldError error : fieldErrors) {
            sb.append("错误字段：" + error.getField() + " ,当前的值 " + error.getRejectedValue() + " ,提示： " + error.getDefaultMessage());
            sb.append(",");
        }
        return sb.toString();
    }

    //处理json对象提交错误
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String exMethodArgNot(MethodArgumentNotValidException e) {
        StringBuilder sb = new StringBuilder();
        List<FieldError> fieldErrors = e.getFieldErrors();
        for (FieldError error : fieldErrors) {
            sb.append("错误字段：" + error.getField() + " ,当前的值 " + error.getRejectedValue() + " ,提示： " + error.getDefaultMessage());
            sb.append(",");
        }
        return sb.toString();
    }
}
