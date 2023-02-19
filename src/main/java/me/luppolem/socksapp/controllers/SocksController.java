package me.luppolem.socksapp.controllers;

import me.luppolem.socksapp.services.SocksService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SocksController {
    private SocksService socksService;

    @GetMapping
    public int getDailyStockAvailability() {
        return socksService.
    }

}
