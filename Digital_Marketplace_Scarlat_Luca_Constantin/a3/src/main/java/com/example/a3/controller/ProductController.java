package com.example.a3.controller;

import com.example.a3.model.Product;
import com.example.a3.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProductController {

    @Autowired
    ProductService productService;

    @RequestMapping(value = "/register_new_product", method = RequestMethod.GET)
    public ModelAndView registration_products() {
        ModelAndView modelAndView = new ModelAndView();
        Product product=new Product();
        modelAndView.addObject("product", product);
        modelAndView.setViewName("register_new_product");
        return modelAndView;
    }

    @RequestMapping(value = "/register_new_product", method = RequestMethod.POST)
    public ModelAndView createNewProduct(String name,String description) {

        ModelAndView modelAndView = new ModelAndView();
        Product product = new Product(name,description);

        productService.saveProduct(product);
        modelAndView.addObject("successMessage", "Product has been registered successfully");
        modelAndView.addObject("product", new Product());
        modelAndView.setViewName("register_new_product");

        return modelAndView;
    }

}
