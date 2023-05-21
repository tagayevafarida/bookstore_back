package com.example.bookstore_back.Controllers;

import com.example.bookstore_back.DataAccessObjects.BookRequest;
import com.example.bookstore_back.Models.Book;
import com.example.bookstore_back.Services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v1/books")
public class BookController {

    private BookService bookService;

    @Autowired
    public BookController(
            BookService bookService
    ){
        this.bookService = bookService;
    }

    @GetMapping("/")
    public List<Book> getBooks(){
        return bookService.getAllBooks();
    }

    @GetMapping("/{bookId}")
    public Book getBook(@PathVariable("bookId") Book book){
        return book;
    }

    @GetMapping(value = "/{bookId}/preview", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getBookPreview(@PathVariable("bookId") Book book){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentLength(book.getPreview().length);
        return new ResponseEntity<>(book.getPreview(), headers, HttpStatus.OK);
    }

    @PostMapping(value = "/create", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Book createBook(@ModelAttribute BookRequest request) throws IOException {
        return bookService.add(request);
    }
}