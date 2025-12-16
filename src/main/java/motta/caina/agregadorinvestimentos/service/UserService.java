package motta.caina.agregadorinvestimentos.service;

import motta.caina.agregadorinvestimentos.controller.dto.AccountResponseDTO;
import motta.caina.agregadorinvestimentos.controller.dto.CreateAccountDTO;
import motta.caina.agregadorinvestimentos.controller.dto.CreateUserDto;
import motta.caina.agregadorinvestimentos.controller.dto.UpdateUserDto;
import motta.caina.agregadorinvestimentos.entity.Account;
import motta.caina.agregadorinvestimentos.entity.BillingAddress;
import motta.caina.agregadorinvestimentos.entity.User;
import motta.caina.agregadorinvestimentos.repository.AccountRepository;
import motta.caina.agregadorinvestimentos.repository.BillingAddressRepository;
import motta.caina.agregadorinvestimentos.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private UserRepository userRepository;
    private BillingAddressRepository billingAddressRepository;
    private AccountRepository accountRepository;

    public UserService(UserRepository repo, BillingAddressRepository billingAddressRepository, AccountRepository accountRepository) {
        this.userRepository = repo;
        this.billingAddressRepository = billingAddressRepository;
        this.accountRepository = accountRepository;
    }

    public UUID createUser(CreateUserDto createUserDto){

        var entity = new User(
                createUserDto.username(),
                createUserDto.email(),
                createUserDto.password()
        );

        var userSaved = userRepository.save(entity);

        return userSaved.getUserId();
    }

    public Optional<User> getUserById(String userId){

    var user = userRepository.findById(UUID.fromString(userId));

    return user;
    }

    public List<User> listUsers(){

        return userRepository.findAll();
    }

    public void deleteById(String userId){

        var id = UUID.fromString(userId);

        var userExists = userRepository.existsById(id);

        if(userExists)
            userRepository.deleteById(id);
    }

    public void updateUserById(String userId, UpdateUserDto updateUserDto){

        var id = UUID.fromString(userId);

        var userEntity = userRepository.findById(id);

        if(userEntity.isPresent()) {
            var user = userEntity.get();

            if (updateUserDto.username() != null) {
                user.setUsername(updateUserDto.username());
            }
            if (updateUserDto.password() != null) {
                user.setPassword(updateUserDto.password());

            }

            userRepository.save(user);
        }
    }

    public void createAccount(String userId, CreateAccountDTO createAccountDTO) {

        var user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var account = new Account(
                UUID.randomUUID(),
                user,
                null,
                createAccountDTO.description(),
                new ArrayList<>()

        );

        var accountCreated = accountRepository.save(account);

        var billingAddress = new BillingAddress(

                accountCreated.getAccountId(),
                account,
                createAccountDTO.street(),
                createAccountDTO.number()
        );

        billingAddressRepository.save(billingAddress);
    }

    public List<AccountResponseDTO> listAccounts(String userId) {

        var user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return user.getAccounts()
                .stream()
                .map(ac -> new AccountResponseDTO(ac.getAccountId().toString(), ac.getDescription()))
                .toList();


    }
}
