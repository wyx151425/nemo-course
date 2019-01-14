package cn.edu.qfnu.rumo.context.exception;

import cn.edu.qfnu.rumo.model.dto.Response;
import cn.edu.qfnu.rumo.util.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Nemo业务异常处理器
 *
 * @author 王振琦
 * createAt: 2018/08/01
 * updateAt: 2018/08/01
 */
@RestControllerAdvice
public class RumoExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(RumoExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public Response<Object> handleException(Exception e) {
        logger.error("NemoExceptionHandler: ", e);
        return new Response<>(StatusCode.SYSTEM_ERROR);
    }

    @ExceptionHandler(value = RumoException.class)
    public Response<Object> handleNemoException(RumoException e) {
        logger.error("NemoExceptionHandler: ", e);
        return new Response<>(e.getStatusCode());
    }
}
