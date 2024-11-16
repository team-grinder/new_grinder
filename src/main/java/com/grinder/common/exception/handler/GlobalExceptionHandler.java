package com.grinder.common.exception.handler;

import com.grinder.common.exception.PasswordValidationException;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PasswordValidationException.class)
    public String handlePasswordValidationException(
            PasswordValidationException e,
            BindingResult bindingResult,
            Model model) {

        bindingResult.rejectValue("password", "error", e.getMessage());
        model.addAttribute("bindingResult", bindingResult);
        return "main/register";
    }
}
