package com.comit.services.areaRestriction.server.exception;

import com.comit.services.areaRestriction.client.response.BaseResponse;
import com.comit.services.areaRestriction.server.constant.AreaRestrictionErrorCode;
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

    @ExceptionHandler(AreaRestrictionCommonException.class)
    public ResponseEntity<BaseResponse> handleCommonEx(AreaRestrictionCommonException ex) {
        return new ResponseEntity<>(new BaseResponse(ex.getCode(), ex.getResponseMessage()), HttpStatus.OK);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<BaseResponse> handleIOException(IOException ex) {
        return new ResponseEntity<>(new BaseResponse(AreaRestrictionErrorCode.INTERNAL_ERROR.getCode(), AreaRestrictionErrorCode.INTERNAL_ERROR.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<BaseResponse> handleNullPointerException(NullPointerException ex) {
        return new ResponseEntity<>(new BaseResponse(AreaRestrictionErrorCode.INTERNAL_ERROR.getCode(), AreaRestrictionErrorCode.INTERNAL_ERROR.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ParseException.class)
    public ResponseEntity<BaseResponse> handleParseException(ParseException ex) {
        return new ResponseEntity<>(new BaseResponse(AreaRestrictionErrorCode.INTERNAL_ERROR.getCode(), AreaRestrictionErrorCode.INTERNAL_ERROR.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InterruptedException.class)
    public ResponseEntity<BaseResponse> handleInterruptedException(InterruptedException ex) {
        return new ResponseEntity<>(new BaseResponse(AreaRestrictionErrorCode.INTERNAL_ERROR.getCode(), AreaRestrictionErrorCode.INTERNAL_ERROR.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
