package exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
   
    @ExceptionHandler(UserNotLoggedInException.class)
    public String handle(UserNotLoggedInException exception) {
        return "redirect:/login"; 
        }
}
