package com.apiit.bangerandco.services;

import com.apiit.bangerandco.models.Booking;
import com.apiit.bangerandco.models.Document;
import com.apiit.bangerandco.models.User;
import com.apiit.bangerandco.repositories.DocumentRepository;
import com.apiit.bangerandco.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private DocumentRepository documentRepo;

    @Autowired
    private UserRepository userRepo;

    @Override
    public ResponseMetadata save(MultipartFile file, String userId) throws IOException {

        Document doc = new Document();
        ResponseMetadata metadata = new ResponseMetadata();

        doc.setDocName(file.getOriginalFilename());
        doc.setFile(file.getBytes());
        Optional<User> user = userRepo.findById(userId);
        doc.setUser(user.get());
        documentRepo.save(doc);
        metadata.setMessage("success");
        metadata.setStatus(200);
        return metadata;
    }

    public ResponseMetadata updateFile(MultipartFile file, String userId) throws IOException{
        ResponseMetadata metadata = new ResponseMetadata();
        Iterable<Document> docs = documentRepo.findAll();
        for(Document document : docs){
            if(document.getUser().getEmail().equals(userId)) {
                Document updatedDoc = document;
                updatedDoc.setDocName(file.getOriginalFilename());
                updatedDoc.setFile(file.getBytes());
                documentRepo.save(updatedDoc);
                metadata.setMessage("success");
                metadata.setStatus(200);
            }
        }
        return metadata;
    }

    @Override
    public byte[] getDocumentFile(String id) {
        long docID=12345678910L;
        Iterable<Document> docs = documentRepo.findAll();
        for(Document doc : docs){
            if(doc.getUser().getEmail().equals(id)){
                docID=doc.getId();
            }
        }
        return documentRepo.findById(docID).get().getFile();
    }

    public Document getDocument(long id) {
        return documentRepo.findById(id).get();
    }

    public ResponseEntity<byte[]>  downloadDoc(long id){

        Document document = documentRepo.findById(id).get();

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.IMAGE_JPEG);
        header.setContentLength(document.getFile().length);
        header.set("Content-Disposition", "attachment; filename=" + document.getDocName());

        return new ResponseEntity<>(document.getFile(), header, HttpStatus.OK);
    }

    @Override
    public List findAll() {
        return (List) documentRepo.findAll();
    }

}
