package com.hautilargi.footman.debug;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class DebugController {

    private final DebugHelperService debugHelperService;

    public DebugController(DebugHelperService debugHelperService) {
        this.debugHelperService = debugHelperService;
    }

    @GetMapping("/api/debug/testmatch")
    public String testMatch(@RequestParam Long homeId, @RequestParam Long awayId) {
        return debugHelperService.playTestMatch(homeId, awayId);
    }

    @GetMapping("/api/debug/squads")
    public String squads() {
        return debugHelperService.describeSquads();
    }
}
