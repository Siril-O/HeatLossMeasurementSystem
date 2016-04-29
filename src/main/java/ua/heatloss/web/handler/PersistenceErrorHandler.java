package ua.heatloss.web.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class PersistenceErrorHandler {

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(CannotCreateTransactionException.class)
    public ModelAndView exceptionHandler(Exception exception,
                                         HttpServletRequest req) {
        ModelAndView mav = new ModelAndView("dbConnectionError");
        mav.addObject("ex", exception);
        mav.addObject("url", req.getRequestURL());
        return mav;
    }
}
