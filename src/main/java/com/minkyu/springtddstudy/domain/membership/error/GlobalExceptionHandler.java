package com.minkyu.springtddstudy.domain.membership.error;

import com.minkyu.springtddstudy.domain.membership.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by MinKyu Kim
 * Created on 2022-11-29.
 **/

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({MembershipException.class})
    public ResponseEntity<?> handleMembershipException(MembershipException exception) {
        return this.createErrorResponse(exception.getErrorResult());
    }

    private ResponseEntity<ErrorResponse> createErrorResponse(final MembershipErrorResult errorResult) {
        return ResponseEntity.status(errorResult.getHttpStatus())
                .body(new ErrorResponse(errorResult.name(), errorResult.getMessage()));
    }
}
