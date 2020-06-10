package com.apiit.bangerandco.controllers;

import com.apiit.bangerandco.models.Document;
import com.apiit.bangerandco.models.User;
import com.apiit.bangerandco.services.DocumentServiceImpl;
import com.apiit.bangerandco.services.ResponseMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    ResponseMetadata handleFileUpload(@RequestParam(value="file") MultipartFile file,
                                      @RequestParam(value="userId") String userId,
                                      @RequestParam(value="fileType") String fileType) throws IOException {
        return documentService.save(file, userId, fileType);
    }

    @RequestMapping(value = "/updateFile", method = RequestMethod.POST)
    public @ResponseBody
    ResponseMetadata handleFileUpdate(@RequestParam(value="file") MultipartFile file,
                                      @RequestParam(value="Id") long fileId) throws IOException {
        return documentService.updateFile(file, fileId);
    }

    @PostMapping("/getDocument")
    public ResponseEntity<Document> GetDocument(@RequestBody Document document){
        return documentService.getDocument(document.getId());
    }

    @PostMapping("/getUserDocuments")
    public ResponseEntity<List<Document>> GetUserDocuments(@RequestBody User user){
        return documentService.getUserDocuments(user.getEmail());
    }

    @RequestMapping(value = "/getDocumentFile/{id}", method = RequestMethod.GET)
    public HttpEntity getDocumentFile(@PathVariable String id) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity(documentService.getDocumentFile(id), httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "/getDocumentByType/{id}", method = RequestMethod.POST)
    public HttpEntity getDocumentByType(@PathVariable String id, @RequestBody Document document) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity(documentService.getDocumentFileByType(id, document.getDocType()), httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    List getDocuments() {
        return documentService.findAll();
    }

    @GetMapping(value = "/download/{id}")
    public ResponseEntity<byte[]> getRandomFile(@PathVariable long id) {
        return documentService.downloadDoc(id);
    }

}

