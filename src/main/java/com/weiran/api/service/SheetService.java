package com.weiran.api.service;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class SheetService {

    private final Sheets sheetsClient;

    public SheetService(Sheets sheetsClient) {
        this.sheetsClient = sheetsClient;
    }

    public String readSheets() throws IOException {
        final String spreadsheetId = "13tP2e_I9VOc7rFRQilTgehZNDRleaD6g0jSph54hiQY";
        final String range = "B2:C4";
        String result;
        ValueRange response = sheetsClient.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();
        List<List<Object>> values = response.getValues();
        if (values == null || values.isEmpty()) {
            result = "No data found.";
        } else {
//            values.forEach((row) -> result = row.toString());
            result = values.get(0).toString();
        }
        return result;
    }
}
