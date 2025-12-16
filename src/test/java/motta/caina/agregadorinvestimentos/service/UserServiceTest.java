package motta.caina.agregadorinvestimentos.service;

import motta.caina.agregadorinvestimentos.controller.dto.CreateUserDto;
import motta.caina.agregadorinvestimentos.controller.dto.UpdateUserDto;
import motta.caina.agregadorinvestimentos.entity.User;
import motta.caina.agregadorinvestimentos.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository repo;

    @InjectMocks
    private UserService service;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Captor
    private ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Nested
    class createUser{

        @Test
        @DisplayName("Should create a user with success")
        void shouldCreateAUserWithSuccess(){

            //Arrange
            var user = new User(
                    UUID.randomUUID(),
                    "username",
                    "email@email.com",
                    "senha123",
                    Instant.now(),
                    null
            );

            doReturn(user).when(repo).save(userArgumentCaptor.capture());
            var input = new CreateUserDto("username", "email@email.com", "password");

            //Act
            var output = service.createUser(input);

            //Assert
            assertNotNull(output);

            var userCaptured = userArgumentCaptor.getValue();
            assertEquals(input.username(), userCaptured.getUsername());
            assertEquals(input.email(), userCaptured.getEmail());
            assertEquals(input.password(), userCaptured.getPassword());
        }

        @Test
        @DisplayName("Should throw exception when error occurs")
        void shouldThrowExceptionWhenErrorOccurs(){

            //Arrange
            doThrow(new RuntimeException()).when(repo).save(any());
            var user = new CreateUserDto(
                    "username",
                    "email@email.com",
                    "senha123"
            );

            //Act and Assert
            assertThrows(RuntimeException.class, () -> service.createUser(user));
        }
    }

    @Nested
    class getUserById{

        @Test
        @DisplayName("Should get user by id successfully when optional is present")
        void shouldGetUserByIdSuccessfullyWhenOptionalIsPresent(){

            //Arrange
            var user = new User(
                    UUID.randomUUID(),
                    "username",
                    "email@email.com",
                    "senha123",
                    Instant.now(),
                    null
            );
            doReturn(Optional.of(user)).when(repo).findById(uuidArgumentCaptor.capture());

        //Act
        var output = service.getUserById(user.getUserId().toString());

         //Assert
            assertTrue(output.isPresent());
            assertEquals(user.getUserId(), uuidArgumentCaptor.getValue());
        }

        @Test
        @DisplayName("Should get user by id successfully when optional is empty")
        void shouldGetUserByIdSuccessfullyWhenOptionalIsEmpty(){

            //Arrange
            var userId = UUID.randomUUID();
            doReturn(Optional.of(userId)).when(repo).findById(uuidArgumentCaptor.capture());

            //Act
            var output = service.getUserById(userId.toString());

            //Assert
            assertTrue(output.isEmpty());
            assertEquals(userId, uuidArgumentCaptor.getValue());
        }

        @Nested

        class listUsers{

            @Test
            @DisplayName("should return all users with success")
            void shoudReturnAllUsersWithSuccess(){
                var user = new User(
                        UUID.randomUUID(),
                        "username",
                        "email@email.com",
                        "senha123",
                        Instant.now(),
                        null
                );
                var userList = List.of(user);
                doReturn(List.of(user)).when(repo).findAll();

                //Act
                var output = service.listUsers();

                //Assert
                assertNotNull(output);
                assertEquals(userList.size(), output.size());
            }
        }
    }

    @Nested
    class deleteById{

        @Test
        @DisplayName("Should delete user with success when user exists")
        void shouldDeleteUserWithSuccessWhenUserExists(){

            //Arrange
            doReturn(true).when(repo).existsById(uuidArgumentCaptor.capture());
            doNothing().when(repo).deleteById(uuidArgumentCaptor.capture());

            var userId = UUID.randomUUID();
            //Act
            service.getUserById(userId.toString());

            //Assert
            var idList = uuidArgumentCaptor.getAllValues();
            assertEquals(userId, idList.get(0));
            assertEquals(userId, idList.get(1));

            verify(repo, times(1)).existsById(idList.get(0));
            verify(repo, times(1)).existsById(idList.get(1));


        }

        @Test
        @DisplayName("Should not delete user when user dont exists")
        void shouldNotDeleteUserWhenUserDontExists(){

            //Arrange
            doReturn(false).when(repo).existsById(uuidArgumentCaptor.capture());

            var userId = UUID.randomUUID();
            //Act
           service.deleteById(userId.toString());

            //Assert
            assertEquals(userId, uuidArgumentCaptor.capture());

            verify(repo, times(1)).existsById(uuidArgumentCaptor.getValue());
            verify(repo, times(1)).deleteById(any());


        }
    }

    @Nested
    class updateUserById{

        @Test
        @DisplayName("Should update user by id when user exists and username and password is filled")
        void shouldUpdateUserByIdWhenUserExistsAndUsernameAndPasswordIsFilled(){

            //Arrange
            var updateUserDto = new UpdateUserDto(

                    "newusername",
                    "newpassword"
            );

            var user = new User(
                    UUID.randomUUID(),
                    "username",
                    "email@email.com",
                    "senha123",
                    Instant.now(),
                    null
            );
            doReturn(Optional.of(user)).when(repo).findById(uuidArgumentCaptor.capture());
            doReturn(user).when(repo).save(userArgumentCaptor.capture());


            //Act
            service.updateUserById(user.getUserId().toString(), updateUserDto);

            //Assert
            assertEquals(user.getUserId(), uuidArgumentCaptor.getValue());

            var userCaptured = userArgumentCaptor.getValue();

            assertEquals(user.getUsername(), userCaptured.getUsername());
            assertEquals(user.getPassword(), userCaptured.getPassword());

            verify(repo, times(1)).findById(uuidArgumentCaptor.getValue());
            verify(repo, times(1)).save(user);

        }

        @Test
        @DisplayName("Should not update user when user not exists")
        void shouldNotUpdateUserWhenUserNotExists(){

            //Arrange
            var updateUserDto = new UpdateUserDto(

                    "newusername",
                    "newpassword"
            );

            var userId = UUID.randomUUID();

            doReturn(Optional.empty()).when(repo).findById(uuidArgumentCaptor.capture());

            //Act
            service.updateUserById(userId.toString(), updateUserDto);

            //Assert
            assertEquals(userId.toString(), uuidArgumentCaptor.getValue());

            verify(repo, times(1)).findById(uuidArgumentCaptor.getValue());
            verify(repo, times(1)).save(any());

        }


    }


}