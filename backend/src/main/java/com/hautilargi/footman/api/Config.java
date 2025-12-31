package com.hautilargi.footman.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hautilargi.footman.core.service.ConfigurationService;

//import com.hautilargi.footman.model.MatchRepository;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class Config {

   @Autowired
   ConfigurationService cs;

    @GetMapping("/api/config/status")
    public String getStatus(@RequestParam String status) {
        try {
            cs.setStatus(status);
        } catch (Exception e) {
            return e.getMessage();
        }
        return "OK";
    }

        @GetMapping("/api/config/sleep")
    public String setSleep() {
        try {
            cs.setStatus("SLEEP");
        } catch (Exception e) {
            return e.getMessage();
        }
        return "OK";
    }

    @GetMapping("/api/config/wake")
    public String setWake() {
        try {
            cs.setStatus("OK");
        } catch (Exception e) {
            return e.getMessage();
        }
        return "OK";
    }

}