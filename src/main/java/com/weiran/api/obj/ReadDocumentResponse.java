package com.weiran.api.obj;

import com.google.api.services.docs.v1.model.Document;

public class ReadDocumentResponse {
    private Document document;
    private Integer endIndex;

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public Integer getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(Integer endIndex) {
        this.endIndex = endIndex;
    }
}
