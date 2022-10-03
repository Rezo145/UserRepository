package com.example.demo.other;

import com.example.demo.controller.UserController;
import com.example.demo.entity.User;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import java.util.ArrayList;
import java.util.List;

public class UserResourceAssembler
        extends RepresentationModelAssemblerSupport<User, UserResource> {
    public UserResourceAssembler() {
        super(UserController.class, UserResource.class);
    }

    protected UserResource instantiateResource(User user) {
        return new UserResource(user);
    }

    public UserResource toResource(User user) {
        return createModelWithId(user.getID(),user);
    }

    public List<UserResource> toResources(List<User> users) {
        List<UserResource> userResourceList = new ArrayList<>();
        for (int i=0;i<users.size();i++){
            userResourceList.add(toResource(users.get(i)));
        }
        return userResourceList;
    }

    @Override
    public UserResource toModel(User entity) {
        return null;
    }
}