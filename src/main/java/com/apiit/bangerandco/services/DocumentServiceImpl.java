package com.apiit.bangerandco.services;

import com.apiit.bangerandco.models.Document;
import com.apiit.bangerandco.models.User;
import com.apiit.bangerandco.repositories.DocumentRepository;
import com.apiit.bangerandco.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
        doc.setDocName(file.getOriginalFilename());
        doc.setFile(file.getBytes());
        Optional<User> user = userRepo.findById(userId);
        doc.setUser(user.get());
        documentRepo.save(doc);
        ResponseMetadata metadata = new ResponseMetadata();
        metadata.setMessage("success");
        metadata.setStatus(200);
        return metadata;
    }

    @Override
    public byte[] getDocumentFile(Long id) {
        return documentRepo.findById(id).get().getFile();
    }

    @Override
    public List findAll() {
        return (List) documentRepo.findAll();
    }

}
