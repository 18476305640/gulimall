package com.zhuangjie.gulimall.product.exception;

import com.zhuangjie.common.exception.BizCodeEnume;
import com.zhuangjie.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
@Slf4j
@RestControllerAdvice(basePackages = "com.zhuangjie.gulimall.product.controller")
public class MallExceptionControllerAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R handleValidException(MethodArgumentNotValidException e) {
        log.error("数据校验出现问题{}，异常类型：{}",e.getMessage(),e.getClass()); //记录

        HashMap<String, String> errMap = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(item->{
            String field = item.getField();
            String message = item.getDefaultMessage();
            errMap.put(field,message);
        });
        System.out.println("统一异常处理"+e.getClass());
        return R.error(BizCodeEnume.VAILD_EXCEPTION.getCode(), BizCodeEnume.VAILD_EXCEPTION.getMsg()).put("data",errMap);
    }
    @ExceptionHandler(Exception.class)
    public R allException(Exception e) {
        log.error("出现问题{}，异常类型：{}",e.getMessage(),e.getClass());
        return R.error(BizCodeEnume.UNKNOW_EXCEPTION.getCode(), BizCodeEnume.UNKNOW_EXCEPTION.getMsg());
    }

}
