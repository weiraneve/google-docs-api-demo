package com.weiran.api.controller;

import com.weiran.api.service.SheetService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("sheets")
public class SheetsController {

    private final SheetService sheetService;

    public SheetsController(SheetService sheetService) {
        this.sheetService = sheetService;
    }

    @GetMapping()
    public String getSheets() throws IOException {
        return sheetService.readSheets();
    }

}
