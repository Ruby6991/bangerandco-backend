package com.apiit.bangerandco.services;

import com.apiit.bangerandco.models.Document;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface DocumentService {

    ResponseMetadata save(MultipartFile multipartFile, String UserId) throws IOException;

    byte[] getDocumentFile(String id);

    List<Document> findAll();
}
