package ar.com.thiagoianuzzi.stock.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
    @RequestMapping("/error")
    public String error(Model model, HttpServletRequest request) {
        model.addAttribute("code", request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE).toString());
        return "error";
    }
}