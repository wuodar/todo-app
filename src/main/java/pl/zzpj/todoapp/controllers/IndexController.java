package pl.zzpj.todoapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Controller
public class IndexController {

    @GetMapping("/helloWorld/{name}")
    public String helloWorld(@PathVariable String name, Model model, HttpServletRequest request) {
        model.addAttribute("name", name);
        return "index";
    }
}
