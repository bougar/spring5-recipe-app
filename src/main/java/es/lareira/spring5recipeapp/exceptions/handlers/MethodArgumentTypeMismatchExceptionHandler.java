package es.lareira.spring5recipeapp.exceptions.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class MethodArgumentTypeMismatchExceptionHandler {

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ModelAndView handleServiceCallException(MethodArgumentTypeMismatchException e) {
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("errorPage");
    modelAndView.addObject("exception", e);
    modelAndView.addObject("statusMessage", "400 " + HttpStatus.BAD_REQUEST.getReasonPhrase());
    return modelAndView;
  }
}
