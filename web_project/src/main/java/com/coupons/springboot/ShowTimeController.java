package com.coupons.springboot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.text.DateFormat;
import java.util.Date;

@Controller
public class ShowTimeController {

    @RequestMapping("/api/showtime")
    public ModelAndView ShowTime(Model m){
        m.addAttribute("now", DateFormat.getDateTimeInstance().format(new Date()));
        System.out.println("运行到 flag 1");

        return new ModelAndView("showtime");
    }
}
