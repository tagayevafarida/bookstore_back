package com.example.bookstore_back.Controllers;

import com.example.bookstore_back.Models.Basket;
import com.example.bookstore_back.Enums.BasketStatus;
import com.example.bookstore_back.Models.Book;
import com.example.bookstore_back.Models.User;
import com.example.bookstore_back.Services.BasketService;
import com.example.bookstore_back.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v1/baskets")
public class BasketController {

    private BasketService basketService;
    private UserService userService;

    @Autowired
    public BasketController(
            BasketService basketService,
            UserService userService
    ){
        this.basketService = basketService;
        this.userService = userService;
    }
    @ResponseBody
    @GetMapping("/")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Basket> getBaskets(){
        if (basketService.baskets().size() == 0) {
            basketService.create();
            return basketService.baskets();
        }
        return basketService.baskets();
    }

    @ResponseBody
    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public void addBasket(Authentication auth){
        User user = userService.getUserByUsername(auth.getName());
        Basket basket = basketService.create();
        userService.addBasketToUser(user, basket);
    }

    @ResponseBody
    @GetMapping("/current/basket")
    @PreAuthorize("isAuthenticated()")
    public Basket getCurrentBasket(Authentication auth){
        User user = userService.getUserByUsername(auth.getName());
        return userService.getCurrentBasket(user);
    }

    @ResponseBody
    @GetMapping("/user")
    @PreAuthorize("isAuthenticated()")
    public List<Basket> getBasketsOfUser(Authentication auth){
        User user = userService.getUserByUsername(auth.getName());
        return user.getBaskets();
    }

    @ResponseBody
    @GetMapping("/{basketId}/add/{bookId}")
    @PreAuthorize("isAuthenticated()")
    public void addBook(@PathVariable("basketId") Basket basket, @PathVariable("bookId") Book book){
        basketService.addBookToBasket(basket, book);
    }

    @ResponseBody
    @GetMapping("/{basketId}/remove/{bookId}")
    @PreAuthorize("isAuthenticated()")
    public void removeBook(@PathVariable("basketId") Basket basket, @PathVariable("bookId") Book book){
        basketService.removeBookFromBasket(basket, book);
    }

    @ResponseBody
    @GetMapping("/{basketId}/change-status")
    @PreAuthorize("isAuthenticated()")
    public Basket changeStatus(@PathVariable("basketId") Basket basket){
        basketService.changeStatus(basket.getId(), BasketStatus.REMOVED.name());
        return basket;
    }

}
