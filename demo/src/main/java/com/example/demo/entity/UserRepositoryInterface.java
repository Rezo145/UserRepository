package com.example.demo.entity;


import javax.persistence.criteria.CriteriaBuilder;

public interface UserRepositoryInterface {
    Iterable<User> findAll();
    User findOne(String id);
    User save(User user);

    void deleteById(Integer id);

    void updateUser(User updatedUser);

    User findWithFirstName(String firstName);
}
