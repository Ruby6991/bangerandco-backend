package com.apiit.bangerandco.controllers;

import com.apiit.bangerandco.models.Document;
import com.apiit.bangerandco.services.DocumentServiceImpl;
import com.apiit.bangerandco.services.ResponseMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping(value = "/doc")
@CrossOrigin(value = {"*"}, exposedHeaders = {"Content-Disposition"})
public class DocumentController {

    private static final Logger LOG = LoggerFactory.getLogger(DocumentController.class);

    @Autowired
    DocumentServiceImpl documentService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public @ResponseBody
    ResponseMetadata handleFileUpload(@RequestParam(value="file") MultipartFile file, @RequestParam(value="userId") String userId) throws IOException {
        return documentService.save(file, userId);
    }

    @RequestMapping(value = "/getDocument/{id}", method = RequestMethod.GET)
    public HttpEntity getDocument(@PathVariable String id) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity(documentService.getDocumentFile(id), httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    List getDocument() {
        return documentService.findAll();
    }

    @GetMapping(value = "/download/{id}")
    public ResponseEntity<byte[]> getRandomFile(@PathVariable long id) {
        return documentService.downloadDoc(id);
    }

    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable long fileId) {
        // Load file from database
        Document dbFile = documentService.getDocument(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; docName=\"" + dbFile.getDocName() + "\"")
                .body(new ByteArrayResource(dbFile.getFile()));
    }

}

