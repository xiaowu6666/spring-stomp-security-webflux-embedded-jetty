package org.ting.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;
import org.ting.spring.model.Result;
import org.ting.spring.model.User;
import org.ting.spring.service.UserService;
import org.ting.spring.utils.Online;
import org.ting.spring.utils.SecurityAuth;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;

import static org.ting.spring.model.Result.success;

@RestController
@RequestMapping("/api/user")
public class UserWebFluxController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityAuth securityAuth;

    @GetMapping("online")
    public Flux<User> inlineUsers() {
        Collection<User> users = Online.onlineUsers();
        return Flux.fromIterable(users);
    }

    @GetMapping("existsuname")
    public Mono<Integer> existsUname(@RequestParam String username){
        User user = userService.findByUname(username);
        if (user == null)
            return Mono.just(200);
        else return Mono.just(304); //表示已经注册了
    }

    @GetMapping("existsemail")
    public Mono<Integer> existsEmail(@RequestParam String email){
        User user = userService.findByEmail(email);
        if (user == null)
            return Mono.just(200);
        else return Mono.just(304); //表示已经注册了
    }

    @PostMapping("register")
    public Mono<Result> register(@RequestParam String username,@RequestParam String email,@RequestParam String password) {
        User user = new User();
        user.setPassword(password);
        user.setEmail(HtmlUtils.htmlEscape(email));
        user.setUsername(HtmlUtils.htmlEscape(username));
        userService.insert(user);
        return Mono.just(success());
    }

    @GetMapping("/findall")
    public Flux<User> findAll() {
        List<User> all = userService.findAll();
        return Flux.fromIterable(all);
    }

    @GetMapping("loginuser")
    public Mono<User> loginUser() {
        return Mono.just(securityAuth.getUser());
    }
}
