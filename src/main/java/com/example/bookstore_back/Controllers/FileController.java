package com.example.bookstore_back.Controllers;

import com.example.bookstore_back.Services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v1/files")
public class FileController {

    private FileService fileService;

    @Autowired
    public FileController(
            FileService fileService
    ){
        this.fileService = fileService;
    }
}
