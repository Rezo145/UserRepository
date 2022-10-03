package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.entity.UserFirstNameAndLastNameDTO;
import com.example.demo.entity.UserRepository;
import com.example.demo.other.UserResource;
import com.example.demo.other.UserResourceAssembler;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.time.Clock;
import java.util.List;
import java.util.Optional;

@RepositoryRestController
@RequestMapping("/users")
public class UserController {
    UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<UserResource> createUser(@RequestBody User user) {
        MultiValueMap<String,String> headers = new HttpHeaders();
        headers.add("Code","Created the user");
        headers.add("Time",Clock.systemUTC().instant().toString());

        UserResource userResource = new UserResourceAssembler().toResource(userRepository.save(user));
        return new ResponseEntity<>(userResource,headers,HttpStatus.CREATED);
    }
/*
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Integer id) throws Exception{
            Optional<User> optionalUser = userRepository.findOne(id.toString());
            if(optionalUser.isPresent()) {
                return new ResponseEntity<>(userRepository.findOne(id.toString()).get(),HttpStatus.OK);
            }
        return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    } */

    @GetMapping("/all")
    public ResponseEntity<CollectionModel<UserResource>> getAllUsers() {
        MultiValueMap<String,String> headers = new HttpHeaders();
        headers.add("Code","Got all users");
        headers.add("Time",Clock.systemUTC().instant().toString());

        List<User> users = userRepository.findAll();

        List<UserResource> userResourceList = new UserResourceAssembler().toResources(users);;

        CollectionModel<UserResource> userResourceCollectionModel =
                CollectionModel.of(userResourceList);
        //CollectionModel.wrap((userRepository.findAll()));
        userResourceCollectionModel.add(
        WebMvcLinkBuilder.linkTo(UserController.class)
                .slash("all")
                .withRel("all"));;
        return new ResponseEntity<>(userResourceCollectionModel,headers,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResource> updateUser(@RequestBody User updatedUser){
        MultiValueMap<String,String> headers = new HttpHeaders();
        headers.add("Code","Updated the user");
        headers.add("Time",Clock.systemUTC().instant().toString());
        UserResource userResource= new UserResourceAssembler().toResource(userRepository.save(updatedUser));
        return new ResponseEntity<>(userResource,headers,HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResource> updateUserFirstAndLastName(@RequestBody UserFirstNameAndLastNameDTO userFirstNameAndLastName){
        MultiValueMap<String,String> headers = new HttpHeaders();
        headers.add("Code","Updated the user's first and last names");
        headers.add("Time",Clock.systemUTC().instant().toString());

        UserResource userResource= new UserResourceAssembler().toResource(userRepository.findWithFirstNameAndLastName(userFirstNameAndLastName));

        return new ResponseEntity<>(userResource,headers,HttpStatus.OK);
    }

    @GetMapping("/{number}")
    public ResponseEntity<CollectionModel<UserResource>> getUsersPage(@PathVariable Integer number) {
        MultiValueMap<String,String> headers = new HttpHeaders();
        headers.add("Code","Got the page of users");
        headers.add("Time",Clock.systemUTC().instant().toString());
        PageRequest page = PageRequest.of(
                0, number);

        List<User> users = userRepository.findAll(page).getContent();

        List<UserResource> userResourceList = new UserResourceAssembler().toResources(users);;

        CollectionModel<UserResource> userResourceCollectionModel =
                CollectionModel.of(userResourceList);

        userResourceCollectionModel.add(
                WebMvcLinkBuilder.linkTo(UserController.class)
                        .slash("{number}")
                        .withRel("usersPage"));;
        return new ResponseEntity<>(userResourceCollectionModel,headers,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResource> getUserById(@PathVariable("id") Integer id) {
        MultiValueMap<String,String> headers = new HttpHeaders();
        headers.add("Code","Got the user");
        headers.add("Time",Clock.systemUTC().instant().toString());
        Optional<User> optUser = userRepository.findById(id);

        if (optUser.isPresent()) {
            UserResource userResource= new UserResourceAssembler().toResource(optUser.get());

            return new ResponseEntity<>(userResource,headers,HttpStatus.OK);
        }
        headers.clear();
        headers.add("Code","Couldn't find the user");
        headers.add("Time",Clock.systemUTC().instant().toString());
        return new ResponseEntity<>(null,headers, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/dto/{id}")
    public ResponseEntity<UserFirstNameAndLastNameDTO> getFirstNameAndLastName(@PathVariable Integer id) {
            ResponseEntity<UserFirstNameAndLastNameDTO> responseEntity = null;

            User user = userRepository.getById(id);
            UserFirstNameAndLastNameDTO userFirstNameAndLastNameDTO =
                    new UserFirstNameAndLastNameDTO(user.getFirstName(),user.getLastName());

            MultiValueMap<String,String> headers = new HttpHeaders();
            headers.add("Code","Got the user's first and last names");
            headers.add("Time",Clock.systemUTC().instant().toString());

            responseEntity = new ResponseEntity<>(userFirstNameAndLastNameDTO,headers,HttpStatus.OK);

            return responseEntity;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserResource> deleteUser(@PathVariable("id") Integer id) {

            userRepository.deleteById(id);
            MultiValueMap<String,String> headers = new HttpHeaders();
            headers.add("Code","Deleted the user");
            headers.add("Time",Clock.systemUTC().instant().toString());

        return new ResponseEntity<>(null,headers,HttpStatus.NO_CONTENT);

    }

}
