package com.example.demo.other;

import com.example.demo.entity.User;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.Column;
import javax.persistence.Id;

@Relation(value="user", collectionRelation="users")
public class UserResource extends RepresentationModel<UserResource> {
    @Getter
    private String userName;

    @Getter
    private String password;

    @Getter
    private String firstName;

    @Getter
    private String lastName;

    @Getter
    private Integer age;

    public UserResource(User user) {
        this.userName = user.getUserName();
        this.password = user.getPassword();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.age = user.getAge();
    }
}