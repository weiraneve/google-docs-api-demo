package com.weiran.api.service;

import com.weiran.api.obj.TextRequest;
import com.weiran.api.obj.ReadDocumentResponse;
import com.google.api.services.docs.v1.Docs;
import com.google.api.services.docs.v1.model.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DocsService {
    private final Docs docsClient;

    public DocsService(Docs docsClient) {
        this.docsClient = docsClient;
    }

    public ReadDocumentResponse readDocument(String documentId) throws IOException {
        Document document = docsClient.documents().get(documentId).execute();

        List<StructuralElement> structuralElements = document.getBody().getContent();
        Integer endIndex = structuralElements.get(structuralElements.size() - 1).getEndIndex();
        ReadDocumentResponse readDocumentResponse = new ReadDocumentResponse();
        readDocumentResponse.setDocument(document);
        readDocumentResponse.setEndIndex(endIndex);

        return readDocumentResponse;
    }

    public BatchUpdateDocumentResponse addText(TextRequest textRequest, String documentId) throws IOException {
        ReadDocumentResponse readDocumentResponse = readDocument(documentId);
        Integer startIndex = readDocumentResponse.getEndIndex() - 1;
        int endIndex;

        List<Request> requests = new ArrayList<>();

        for (String text : textRequest.getTexts()) {
            InsertTextRequest insertTextRequest = new InsertTextRequest().setText(text).setLocation(new Location().setIndex(startIndex));
            endIndex = startIndex + text.length();

            Range range = new Range().setStartIndex(startIndex).setEndIndex(endIndex);

            UpdateTextStyleRequest updateTextStyleRequest = new UpdateTextStyleRequest().setTextStyle(new TextStyle().setWeightedFontFamily(new WeightedFontFamily().setFontFamily("Arial").setWeight(400))
                            .setFontSize(new Dimension().setMagnitude(10.0).setUnit("PT"))
                            .setForegroundColor(new OptionalColor().setColor(new Color().setRgbColor(new RgbColor().setRed(0F).setGreen(0.5F).setBlue(0F)))))
                    .setFields("weightedFontFamily,fontSize,foregroundColor")
                    .setRange(range);

            UpdateParagraphStyleRequest updateParagraphStyleRequest = new UpdateParagraphStyleRequest().setParagraphStyle(
                            new ParagraphStyle().setNamedStyleType("NORMAL_TEXT").setDirection("LEFT_TO_RIGHT"))
                    .setFields("namedStyleType,direction");
            updateParagraphStyleRequest.setRange(range);

            Request requestInsertText = new Request().setInsertText(insertTextRequest);
            Request requestUpdateParagraphStyle = new Request().setUpdateParagraphStyle(updateParagraphStyleRequest);
            Request requestUpdateTextStyle = new Request().setUpdateTextStyle(updateTextStyleRequest);

            requests.add(requestInsertText);
            requests.add(requestUpdateParagraphStyle);
            requests.add(requestUpdateTextStyle);

            startIndex = endIndex;

            requests.add(new Request().setInsertText(new InsertTextRequest().setText("\n").setLocation(new Location().setIndex(startIndex))));
            startIndex++;
        }

        BatchUpdateDocumentRequest batchUpdateDocumentRequest = new BatchUpdateDocumentRequest().setRequests(requests);

        return docsClient.documents().batchUpdate(documentId, batchUpdateDocumentRequest).execute();
    }
}
