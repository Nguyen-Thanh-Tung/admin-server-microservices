package com.comit.services.account.exeption;

import com.comit.services.account.constant.UserErrorCode;
import com.comit.services.account.controller.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.text.ParseException;

@ControllerAdvice
@Service
public class ExceptionHandle {

    private final String BASE_METHOD = this.getClass().getCanonicalName();

    @ExceptionHandler(AccountRestApiException.class)
    public ResponseEntity<BaseResponse> handleCommonEx(AccountRestApiException ex) {
        CommonLogger.error(ex + " " + ex.getResponseMessage());
        return new ResponseEntity<>(new BaseResponse(ex.getCode(), ex.getResponseMessage()), HttpStatus.OK);
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<BaseResponse> handleCommonEx(AuthException ex) {
        CommonLogger.error(ex + " " + ex.getResponseMessage());
        return new ResponseEntity<>(new BaseResponse(ex.getCode(), ex.getResponseMessage()), HttpStatus.OK);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<BaseResponse> handleIOException(IOException ex) {
        CommonLogger.error(ex + " " + ex.getMessage());
        return new ResponseEntity<>(new BaseResponse(UserErrorCode.INTERNAL_ERROR), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<BaseResponse> handleNullPointerException(NullPointerException ex) {
        CommonLogger.error(ex + " " + ex.getMessage());
        return new ResponseEntity<>(new BaseResponse(UserErrorCode.INTERNAL_ERROR), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ParseException.class)
    public ResponseEntity<BaseResponse> handleParseException(ParseException ex) {
        CommonLogger.error(ex + " " + ex.getMessage());
        return new ResponseEntity<>(new BaseResponse(UserErrorCode.INTERNAL_ERROR), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InterruptedException.class)
    public ResponseEntity<BaseResponse> handleInterruptedException(InterruptedException ex) {
        CommonLogger.error(ex + " " + ex.getMessage());
        return new ResponseEntity<>(new BaseResponse(UserErrorCode.INTERNAL_ERROR), HttpStatus.BAD_REQUEST);
    }
}
