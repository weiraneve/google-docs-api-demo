package com.weiran.api.controller;

import com.weiran.api.obj.TextRequest;
import com.weiran.api.obj.ReadDocumentResponse;
import com.weiran.api.service.DocsService;
import com.google.api.services.docs.v1.model.BatchUpdateDocumentResponse;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("documents")
public class DocsController {

    private final DocsService docsService;

    public DocsController(DocsService docsService) {
        this.docsService = docsService;
    }

    @GetMapping("/{id}")
    public ReadDocumentResponse readDocument(@PathVariable String id) throws IOException {
        return docsService.readDocument(id);
    }

    @PutMapping("/{id}")
    public BatchUpdateDocumentResponse updateDocument(@RequestBody TextRequest request, @PathVariable String id) throws IOException {
        return docsService.addText(request, id);
    }
}
