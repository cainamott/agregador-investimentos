package motta.caina.agregadorinvestimentos.controller;

import motta.caina.agregadorinvestimentos.controller.dto.AccountResponseDTO;
import motta.caina.agregadorinvestimentos.controller.dto.CreateAccountDTO;
import motta.caina.agregadorinvestimentos.controller.dto.CreateUserDto;
import motta.caina.agregadorinvestimentos.controller.dto.UpdateUserDto;
import motta.caina.agregadorinvestimentos.entity.User;
import motta.caina.agregadorinvestimentos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/v1/users")
public class UserController {


    private UserService userService;

    @Autowired
    public UserController(UserService service) {
        this.userService = service;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody CreateUserDto createUserDto){

        var userId = userService.createUser(createUserDto);

        return ResponseEntity.created(URI.create("/v1/users" + userId.toString())).build();
    }


    @GetMapping(path = "/{userId}")
    public ResponseEntity<User> getUserbyId(@PathVariable("userId") String userId){

        var user = userService.getUserById(userId);
        if (user.isPresent())
            return ResponseEntity.ok(user.get());
        else
            return ResponseEntity.notFound().build();

    }

    @GetMapping
    public ResponseEntity<List<User>> listUsers (){

        var users = userService.listUsers();

        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteById(@PathVariable("userId") String userId){
        userService.deleteById(userId);

        return ResponseEntity.noContent().build();

    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateUserById(@PathVariable("userId") String userId,
                                               @RequestBody UpdateUserDto updateUserDto){

        userService.updateUserById(userId, updateUserDto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{userId}/accounts")
    public ResponseEntity<Void> createAccount(@PathVariable("userId") String userId,
            @RequestBody CreateAccountDTO createAccountDTO){
        userService.createAccount(userId, createAccountDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}/accounts")
    public ResponseEntity<List<AccountResponseDTO>> listAccounts(@PathVariable("userId") String userId) {

        var account = userService.listAccounts(userId);
        return ResponseEntity.ok().build();

    }

}
