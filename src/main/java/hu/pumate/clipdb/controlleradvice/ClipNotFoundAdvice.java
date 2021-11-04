package hu.pumate.clipdb.controlleradvice;

import hu.pumate.clipdb.exception.ClipNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ClipNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(ClipNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String clipNotFoundHandler(ClipNotFoundException ex) {
        return ex.getMessage();
    }
}
