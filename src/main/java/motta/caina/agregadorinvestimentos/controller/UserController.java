package motta.caina.agregadorinvestimentos.controller;

import motta.caina.agregadorinvestimentos.entity.User;
import motta.caina.agregadorinvestimentos.service.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/v1/users")
public class UserController {


    private UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody CreateUserDto createUserDto){

        var userId = service.createUser(createUserDto);

        return ResponseEntity.created(URI.create("/v1/users" + userId.toString())).build();
    }


    @GetMapping(path = "/{userId}")
    public ResponseEntity<User> getUserbyId(@PathVariable("userId") String userId){

        var user = service.getUserById(userId);
        if (user.isPresent())
            return ResponseEntity.ok(user.get());
        else
            return ResponseEntity.notFound().build();

    }

    @GetMapping
    public ResponseEntity<List<User>> listUsers (){

        var users = service.listUsers();

        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteById(@PathVariable("userId") String userId){
        service.deleteById(userId);

        return ResponseEntity.noContent().build();

    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateUserById(@PathVariable("userId") String userId,
                                               @RequestBody UpdateUserDto updateUserDto){

        service.updateUserById(userId, updateUserDto);
        return ResponseEntity.noContent().build();
    }
}
