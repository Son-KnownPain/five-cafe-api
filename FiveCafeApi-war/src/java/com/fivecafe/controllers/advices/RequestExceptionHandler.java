package com.fivecafe.controllers.advices;

import com.fivecafe.exceptions.UnauthorizedException;
import com.fivecafe.models.responses.InvalidResponse;
import com.fivecafe.models.responses.StandardResponse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice(annotations = RestController.class)
public class RequestExceptionHandler {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<InvalidResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            errors.add(error.getDefaultMessage());
        }
        InvalidResponse res = new InvalidResponse();
        res.setSuccess(false);
        res.setInvalid(true);
        res.setStatus(400);
        res.setMessage("Request body is invalid data");
        res.setErrors(errors);
        return new ResponseEntity<InvalidResponse>(res, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<StandardResponse> handleUnauthorizedException(UnauthorizedException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
            StandardResponse.builder()
                    .status(401)
                    .success(false)
                    .message("Incorrect username or password")
                    .build()
        );
    }
}
