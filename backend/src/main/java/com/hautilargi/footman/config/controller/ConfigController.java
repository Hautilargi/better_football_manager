package com.hautilargi.footman.config.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hautilargi.footman.core.service.ConfigurationService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ConfigController {

    private final ConfigurationService configurationService;

    public ConfigController(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    @GetMapping("/api/config/status")
    public String getStatus(@RequestParam String status) {
        return changeStatus(status);
    }

    @GetMapping("/api/config/sleep")
    public String setSleep() {
        return changeStatus("SLEEP");
    }

    @GetMapping("/api/config/wake")
    public String setWake() {
        return changeStatus("OK");
    }

    private String changeStatus(String status) {
        try {
            configurationService.setStatus(status);
        } catch (Exception e) {
            return e.getMessage();
        }
        return "OK";
    }
}
