package com.example.a3.controller;

import com.example.a3.model.Product;
import com.example.a3.model.Role;
import com.example.a3.model.User;
import com.example.a3.repository.RoleRepository;
import com.example.a3.service.MyUserService;
import com.example.a3.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Set;

@Controller
public class AuthController {


    @Autowired
    private MyUserService userService;

    @Autowired
    private ProductService productService;

    @RequestMapping(value = { "/", "/login" }, method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByUserName(user.getUsername());
        if (userExists != null) {
            bindingResult.rejectValue("userName", "error.user",
                    "There is already a user registered with the user name provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("registration");

        }
        return modelAndView;
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ModelAndView admin() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        modelAndView.addObject("username", "Welcome " + user.getUsername() );
        modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");
        modelAndView.setViewName("admin");
        return modelAndView;
    }

    @RequestMapping(value="/get_all_products",method = RequestMethod.GET)
    public ModelAndView listAllProducts()
    {
        ModelAndView modelAndView = new ModelAndView();
        List<Product> list_products=productService.getAllProducts();
        modelAndView.addObject("list_products",list_products);
        modelAndView.setViewName("get_all_products");
        return modelAndView;
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public ModelAndView test() {
        ModelAndView modelAndView = new ModelAndView();
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        User user = userService.findUserByUserName(auth.getName());
        modelAndView.setViewName("test");
        return modelAndView;
    }

}
