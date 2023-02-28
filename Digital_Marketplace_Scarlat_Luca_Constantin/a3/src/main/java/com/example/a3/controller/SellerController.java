package com.example.a3.controller;

import com.example.a3.model.Product;
import com.example.a3.model.Role;
import com.example.a3.model.User;
import com.example.a3.repository.RoleRepository;
import com.example.a3.service.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;

@Controller
public class SellerController {

    @Autowired
    private MyUserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @RequestMapping(value="/become_seller",method = RequestMethod.GET)

    public ModelAndView become_seller() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("become_seller");
        return modelAndView;
    }

    @RequestMapping(value = "/become_seller", method = RequestMethod.POST)
    public ModelAndView addSellerRole(@RequestParam("username") String username) {
        ModelAndView modelAndView = new ModelAndView();
            User user=userService.findUserByUserName(username);
            userService.becomeSeller(user);
            modelAndView.addObject("user", user);
            modelAndView.addObject("username",username);
            modelAndView.addObject("successMessage", "You are now a seller.");

            modelAndView.setViewName("become_seller");

        return modelAndView;
    }
}
