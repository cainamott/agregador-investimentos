package motta.caina.agregadorinvestimentos.service;

import motta.caina.agregadorinvestimentos.controller.CreateUserDto;
import motta.caina.agregadorinvestimentos.controller.UpdateUserDto;
import motta.caina.agregadorinvestimentos.entity.User;
import motta.caina.agregadorinvestimentos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public UUID createUser(CreateUserDto createUserDto){

        var entity = new User(
                createUserDto.username(),
                createUserDto.email(),
                createUserDto.password()
        );

        var userSaved = repo.save(entity);

        return userSaved.getUserId();
    }

    public Optional<User> getUserById(String userId){

    var user = repo.findById(UUID.fromString(userId));

    return user;
    }

    public List<User> listUsers(){

        return repo.findAll();
    }

    public void deleteById(String userId){

        var id = UUID.fromString(userId);

        var userExists = repo.existsById(id);

        if(userExists)
            repo.deleteById(id);
    }

    public void updateUserById(String userId, UpdateUserDto updateUserDto){

        var id = UUID.fromString(userId);

        var userEntity = repo.findById(id);

        if(userEntity.isPresent()) {
            var user = userEntity.get();

            if (updateUserDto.username() != null) {
                user.setUsername(updateUserDto.username());
            }
            if (updateUserDto.password() != null) {
                user.setPassword(updateUserDto.password());

            }

            repo.save(user);
        }
    }
}
