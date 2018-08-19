package cn.localhost01.exception;

import cn.localhost01.constant.APICode;
import cn.localhost01.constant.APIMsg;
import cn.localhost01.domain.JsonVO;
import cn.localhost01.util.LogUtil;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Map;

/**
 * @Description:全局异常处理类
 * @Author Ran.chunlin
 * @Date: Created in 19:59 2017/12/16
 */
@ControllerAdvice @ResponseBody public class GlobalExceptionHandler {

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class) public Map<String, Object> run(
            HttpRequestMethodNotSupportedException e) {
        LogUtil.getLogger(GlobalExceptionHandler.class).error("【API Global Exception】", e);
        JsonVO jsonVO = new JsonVO();
        return jsonVO.withBase(APICode.METHOD_NOT_SUPPORTED, APIMsg.METHOD_NOT_SUPPORTED);
    }

    @ExceptionHandler(NoHandlerFoundException.class) public Map<String, Object> run(NoHandlerFoundException e) {
        LogUtil.getLogger(GlobalExceptionHandler.class).error("【API Global Exception】", e);
        JsonVO jsonVO = new JsonVO();
        return jsonVO.withBase(APICode.NOT_FOUND, APIMsg.NOT_FOUND);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class) public Map<String, Object> run(
            HttpMessageNotReadableException e) {
        LogUtil.getLogger(GlobalExceptionHandler.class).error("【API Global Exception】", e);
        JsonVO jsonVO = new JsonVO();
        return jsonVO.withBase(APICode.BAD_REQUEST, APIMsg.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class) public Map<String, Object> run(
            HttpMediaTypeNotSupportedException e) {
        LogUtil.getLogger(GlobalExceptionHandler.class).error("【API Global Exception】", e);
        JsonVO jsonVO = new JsonVO();
        return jsonVO.withBase(APICode.MEDIA_NOT_SUPPORTED, APIMsg.MEDIA_NOT_SUPPORTED);
    }

    @ExceptionHandler(Exception.class) public Map<String, Object> run(Exception e) {
        LogUtil.getLogger(GlobalExceptionHandler.class).error("【API Global Exception】", e);
        JsonVO jsonVO = new JsonVO();
        return jsonVO.withBase(APICode.INTERNAL_SERVER_ERROR, APIMsg.INTERNAL_SERVER_ERROR);
    }

}
