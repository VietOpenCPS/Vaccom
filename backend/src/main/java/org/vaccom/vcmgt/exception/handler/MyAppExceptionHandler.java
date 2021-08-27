package org.vaccom.vcmgt.exception.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.vaccom.vcmgt.constant.AppConstant;
import org.vaccom.vcmgt.exception.AuthException;
import org.vaccom.vcmgt.exception.response.BaseExceptionResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author TrinhNX
 * @since 8/27/2021
 */
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class MyAppExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(AuthException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public BaseExceptionResponse handleAuthException(HttpServletRequest request, AuthException e) {
        log.error(request.getRequestURI(), e);
        final String localMessage = messageSource.getMessage("nguoidung.not_exist_or_locked", null, AppConstant.DEFAULT_LOCALE);
        return new BaseExceptionResponse(localMessage);
    }
}
