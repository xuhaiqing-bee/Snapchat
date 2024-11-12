package com.hking.xhq.handler;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @Author：XuHaiqing
 * @Package：com.hking.xhq.handler
 * @Project：Snapchat
 * @name：GlobalExceptionHandler
 * @Date：2024/11/12 22:49
 * @Filename：GlobalExceptionHandler
 * @Description:
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleRuntimeException(RuntimeException e, Model model) {
        model.addAttribute("message", "An error occurred: " + e.getMessage());
        return "error"; // 返回错误页面视图
    }
}
