package com.example.study.controller;

import com.example.study.model.SearchParam;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class GetController {

    @RequestMapping(method = RequestMethod.GET, path = "/getMethod")
    public String getRequest() {
        return "Hi";
    }

    @GetMapping("/getParameter")
    public String getParameter(@RequestParam String id, @RequestParam String password) {
        System.out.println(id);
        System.out.println(password);

        return id + " " + password;
    }

    @GetMapping("/multiParameter")
    public SearchParam getMultiParameter(SearchParam searchParam) {
        return searchParam;
    }
}
