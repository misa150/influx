package com.influx.engine.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
@Slf4j
public class TestController {

    @RequestMapping(method = {RequestMethod.GET}, value = {"/sample-message"})
    public ResponseEntity<String> runtimeTest () {
        return ResponseEntity.ok().body("Test Okay");
    }
}
