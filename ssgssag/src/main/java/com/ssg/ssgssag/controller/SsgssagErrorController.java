package com.ssg.ssgssag.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HandlerInterceptor;


@Log4j2
@Controller
public class SsgssagErrorController implements ErrorController {

  @RequestMapping("/error")
  public String handleError(HttpServletRequest request) {
    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

    if (status != null) {
      int statusCode = Integer.parseInt(status.toString());

      switch (statusCode) {
        case 403:
          return "error/page-error-403"; // 403 에러 페이지 경로
        case 404:
          return "error/page-error-404"; // 404 에러 페이지 경로, 기존 '400'을 '404'로 정정
        case 500:
          return "error/page-error-500"; // 500 에러 페이지 경로
        case 503:
          return "error/page-error-503"; // 503 에러 페이지 경로
        default:
          return "error/page-error"; // 기본 에러 페이지
      }
    }
    return "error/error"; // 상태 코드가 null인 경우 기본 에러 페이지 반환
  }

}