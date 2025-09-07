package com.easyreach.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderFormController {

    @GetMapping("/orders/new")
    public String newOrder(Model model) {
        return "orders/order_form";
    }
}
