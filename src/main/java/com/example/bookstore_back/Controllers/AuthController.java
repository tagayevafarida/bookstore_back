package com.example.bookstore_back.Controllers;


import com.example.bookstore_back.Configurations.TokenService;
import com.example.bookstore_back.DataAccessObjects.LoginRequest;
import com.example.bookstore_back.DataAccessObjects.LoginResponse;
import com.example.bookstore_back.DataAccessObjects.RegisterRequest;
import com.example.bookstore_back.Models.Book;
import com.example.bookstore_back.Models.User;
import com.example.bookstore_back.Services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v1/auth")
public class AuthController {

    private UserService userService;
    private AuthenticationManager authenticationManager;
    private TokenService tokenService;

    @Autowired
    public AuthController(
            UserService userService,
            AuthenticationManager authenticationManager,
            TokenService tokenService
    ) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @ResponseBody
    @PostMapping(value = "/login", produces = "application/json")
    @PreAuthorize("isAnonymous()")
    public LoginResponse loginPost(@RequestBody LoginRequest request, HttpServletRequest req, HttpServletResponse res){
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
        String token = tokenService.generateToken(auth.getName());
        User user = userService.getUserByUsername(auth.getName());
        return LoginResponse.fromUser(user);
    }

    @ResponseBody
    @PostMapping("/token/login")
    @PreAuthorize("isAuthenticated()")
    public LoginResponse tokenLoginPost(Authentication auth){
        User user = userService.getUserByUsername(auth.getName());
        return LoginResponse.fromUser(user);
    }

    @ResponseBody
    @PostMapping(value = "/register", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @PreAuthorize("isAnonymous()")
    public LoginResponse registerPost(@ModelAttribute RegisterRequest request) throws IOException {
        return userService.registration(request);
    }

    @ResponseBody
    @GetMapping(value = "/user/image/{userId}", produces = MediaType.IMAGE_JPEG_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<byte[]> userImageByUserId(@PathVariable("userId") User user){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentLength(user.getImage().length);
        return new ResponseEntity<>(user.getImage(), headers, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/favourites")
    @PreAuthorize("isAuthenticated()")
    public List<Book> getFavourites(Authentication auth){
        User user = userService.getUserByUsername(auth.getName());
        return userService.getFavourites(user);
    }

    @ResponseBody
    @GetMapping("/favourites/add/{bookId}")
    @PreAuthorize("isAuthenticated()")
    public void addFavourites(Authentication auth, @PathVariable("bookId") Book book){
        User user = userService.getUserByUsername(auth.getName());
        userService.addFavourites(user, book);
    }

    @ResponseBody
    @GetMapping("/favourites/check/{bookId}")
    @PreAuthorize("isAuthenticated()")
    public Boolean checkFavourites(Authentication auth, @PathVariable("bookId") Book book){
        User user = userService.getUserByUsername(auth.getName());
        return userService.checkBookFavourites(user, book);
    }

    @ResponseBody
    @GetMapping("/favourites/remove/{bookId}")
    @PreAuthorize("isAuthenticated()")
    public void removeFavourites(Authentication auth, @PathVariable("bookId") Book book){
        User user = userService.getUserByUsername(auth.getName());
        userService.removeFavourites(user, book);
    }

}
