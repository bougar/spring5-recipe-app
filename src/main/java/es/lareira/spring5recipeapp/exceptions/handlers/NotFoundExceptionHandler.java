package es.lareira.spring5recipeapp.exceptions.handlers;

import es.lareira.spring5recipeapp.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
@Slf4j
public class NotFoundExceptionHandler {

  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ModelAndView handleMethodArgumentTypeMismatch(
      NotFoundException notFoundException) {
    log.error("Handling not found exception");
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("errorPage");
    modelAndView.addObject("exception", notFoundException);
    modelAndView.addObject("statusMessage", "404 " + HttpStatus.NOT_FOUND.getReasonPhrase());
    return modelAndView;
  }
}
