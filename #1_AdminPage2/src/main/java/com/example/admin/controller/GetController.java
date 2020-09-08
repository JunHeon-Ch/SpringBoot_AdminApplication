package com.example.admin.controller;

import com.example.admin.model.SearchParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
public class GetController {

    @GetMapping("/getMethod")
    public String getMethod(@RequestParam String id, @RequestParam String password) {
        log.info("id: {}", id);
        log.info("pw: {}", password);

        return id + password;
    }

    @GetMapping("/getMultiParameter")
    public SearchParam getMultiParameter(SearchParam searchParam) {
        log.info("account: {}", searchParam.getAccount());
        log.info("email: {}", searchParam.getEmail());
        log.info("page: {}", searchParam.getPage());

        return searchParam;
    }
}
