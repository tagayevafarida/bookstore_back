package com.example.bookstore_back.Controllers;

import com.example.bookstore_back.Services.BookService;
import com.example.bookstore_back.Services.OrganizationService;
import com.example.bookstore_back.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v1/organizations")
public class OrganizationController {

    private OrganizationService organizationService;
    private UserService userService;
    private BookService bookService;

    @Autowired
    public OrganizationController (
            OrganizationService organizationService,
            UserService userService,
            BookService bookService
    ) {
        this.organizationService = organizationService;
        this.userService = userService;
        this.bookService = bookService;
    }

}
