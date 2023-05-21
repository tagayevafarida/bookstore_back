package com.example.bookstore_back.Services;


import com.example.bookstore_back.Configurations.TokenService;
import com.example.bookstore_back.DataAccessObjects.LoginResponse;
import com.example.bookstore_back.DataAccessObjects.RegisterRequest;
import com.example.bookstore_back.Enums.BasketStatus;
import com.example.bookstore_back.Enums.UserRole;
import com.example.bookstore_back.Models.Basket;
import com.example.bookstore_back.Models.Book;
import com.example.bookstore_back.Models.User;
import com.example.bookstore_back.Repositories.BasketRepository;
import com.example.bookstore_back.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;
    private BasketRepository basketRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private TokenService tokenService;

    @Autowired
    public UserService(
            UserRepository userRepository,
            BasketRepository basketRepository,
            BCryptPasswordEncoder passwordEncoder,
            TokenService tokenService
    ){
        this.userRepository = userRepository;
        this.basketRepository = basketRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    public LoginResponse registration(RegisterRequest request) throws IOException {
        if (userRepository.findUserByUsername(request.getUsername()).isPresent() ||
                userRepository.findUserByEmail(request.getEmail()) != null){
            return null;
        }
        User user = new User(
                request.getFirstname(),
                request.getSecondname(),
                request.getUsername(),
                passwordEncoder.encode(request.getPassword()),
                request.getEmail(),
                tokenService.generateToken(request.getUsername()),
                UserRole.USER.getAuthority(),
                request.getImage().getBytes()
        );
        userRepository.save(
                user
        );
        return LoginResponse.fromUser(user);
    }

    public User getUserByUsername(String username){
        return userRepository.findUserByUsername(username).get();
    }

    public List<Book> getFavourites(User user){
        return user.getFavourites().stream().toList();
    }

    public void addFavourites(User user, Book book){
        user.getFavourites().add(book);
        userRepository.save(user);
    }

    public boolean checkBookFavourites(User user, Book book){
        if (user.getFavourites().contains(book)) {
            return true;
        }
        return false;
    }

    public void removeFavourites(User user, Book book){
        user.getFavourites().remove(book);
        userRepository.save(user);
    }

    public List<Basket> getUserBaskets(User user){
        return user.getBaskets();
    }

    public void addBasketToUser(User user, Basket basket){
        user.getBaskets().add(basket);
        userRepository.save(user);
    }

    public Basket getCurrentBasket(User user){
        List<Basket> baskets = user.getBaskets();
        Basket basket = null;
        for(Basket b:baskets){
            if(b.getStatus().equals(BasketStatus.CREATED.name())){
                basket = b;
            }
        }
        if(basket == null){
            basket = basketRepository.save(new Basket(BasketStatus.CREATED.name()));
            user.getBaskets().add(basket);
            userRepository.save(user);
            return basket;
        }
        return basket;
    }

}
