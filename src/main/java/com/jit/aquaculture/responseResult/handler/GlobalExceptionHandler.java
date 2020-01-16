package com.jit.aquaculture.responseResult.handler;


import com.jit.aquaculture.responseResult.exceptions.BusinessException;
import com.jit.aquaculture.responseResult.result.DefaultErrorResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.nio.file.AccessDeniedException;

//@ControllerAdvice
//public class GlobalExceptionHandler  extends DefaultHandlerExceptionResolver{
//    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
//
//    @ExceptionHandler(value = Exception.class)
//    @ResponseBody
//    public JsonResult defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
//        logger.error("",e);
//        JsonResult jsonResult = new JsonResult();
//        jsonResult.setMessage(e.getMessage());
//        if (e instanceof org.springframework.web.servlet.NoHandlerFoundException) {
//            jsonResult.setCode(404);
//        }
//        if (e instanceof org.springframework.security.authentication.BadCredentialsException){
//            jsonResult.setCode(401);
//        }
//        jsonResult.setRes(null);
//        return jsonResult;
//    }
//}

@RestController
@ControllerAdvice
public class GlobalExceptionHandler extends BaseGlobalExceptionHandler {
    /* 处理400类异常 */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public DefaultErrorResult handleConstraintViolationException(ConstraintViolationException e, HttpServletRequest request) {
        return super.handleConstraintViolationException(e, request);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public DefaultErrorResult handleConstraintViolationException(HttpMessageNotReadableException e, HttpServletRequest request) {
        return super.handleConstraintViolationException(e, request);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public DefaultErrorResult handleBindException(BindException e, HttpServletRequest request) {
        return super.handleBindException(e, request);
    }

    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public DefaultErrorResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        return super.handleMethodArgumentNotValidException(e, request);
    }

    /* 处理自定义异常 */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<DefaultErrorResult> handleBusinessException(BusinessException e, HttpServletRequest request) {
        return super.handleBusinessException(e, request);
    }

    /* 处理运行时异常 */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public DefaultErrorResult handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        //TODO 可通过邮件、微信公众号等方式发送信息至开发人员、记录存档等操作（这个后面我们文章我们单独说明该怎么处理）
        return super.handleRuntimeException(e, request);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public DefaultErrorResult handleAccessDeniedException(HttpServletRequest request,Exception e){
        return super.handleAccessDeniedException(request,e);
    }
}
