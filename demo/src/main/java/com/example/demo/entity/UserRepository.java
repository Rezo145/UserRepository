package com.example.demo.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
public class UserRepository implements JpaRepository<User, Integer> {
    private JdbcTemplate jdbc;

    @Autowired
    public UserRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<User> findAll() {
        return jdbc.query("select id, firstName, lastName from User",
                this::mapRowToUser);
    }

    @Override
    public List<User> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<User> findAllById(Iterable<Integer> integers) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer id) {
        jdbc.query("delete from User where id=?",this::mapRowToUser, id);
    }

    @Override
    public void delete(User entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends User> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends User> S save(S user) {
        jdbc.update(
                "insert into User (userName,password,firstName, lastName,age) values (?, ?, ?,?,?)",
                user.getID()
                ,user.getUserName(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getAge()
        );
        return user;
    }

    @Override
    public <S extends User> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<User> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends User> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends User> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<User> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public User getOne(Integer integer) {
        return null;
    }

    @Override
    public User getById(Integer integer) {
        return null;
    }

    @Override
    public User getReferenceById(Integer integer) {
        return null;
    }

    @Override
    public <S extends User> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    public Optional<User> findOne(String id) {
        return Optional.of(jdbc.queryForObject(
                "select id, firstName, lastName from User where id=?",
                this::mapRowToUser, id));
    }

    @Override
    public <S extends User> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends User> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends User> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends User> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends User> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends User, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    private User mapRowToUser(ResultSet rs, int rowNum)
            throws SQLException {
        return new User(
                rs.getInt("id"),
                rs.getString("firstName"),
                rs.getString("lastName")
        );
    }

    public User findWithFirstNameAndLastName(UserFirstNameAndLastNameDTO firstNameAndLastName) {
        List<User> users = null;
        users = jdbc.query("select * from User where first_name=? and last_name=?",
                this::mapRowToUser,firstNameAndLastName.getFirstName(),firstNameAndLastName.getLastName());
        return users.get(0);
    }

    /*
    private JdbcTemplate jdbc;

    @Autowired
    public UserRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Iterable<User> findAll() {
        return jdbc.query("select id, firstName, lastName from User",
                this::mapRowToUser);
    }

    @Override
    public User findOne(String id) {
        return jdbc.queryForObject(
                "select id, firstName, lastName from User where id=?",
                this::mapRowToUser, id);
    }

    @Override
    public User save(User user) {
        jdbc.update(
                "insert into User (userName,password,firstName, lastName,age) values (?, ?, ?,?,?)",
                user.getID()
                ,user.getUserName(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getAge()
                );
        return user;
    }

    @Override
    public void deleteById(Integer id) {
        jdbc.query("delete from User where id=?",this::mapRowToUser, id);
    }

    @Override
    public void updateUser(User updatedUser) {
        jdbc.update(
                "insert into User (userName,password,firstName, lastName,age) values (?, ?, ?,?,?)",
                updatedUser.getID()
                , updatedUser.getUserName(),
                updatedUser.getPassword(),
                updatedUser.getFirstName(),
                updatedUser.getLastName(),
                updatedUser.getAge()
        );
    }

    @Override
    public User findWithFirstName(String firstName) {
        List<User> users = null;
        users = jdbc.query("select * from User where first_name=?",
                this::mapRowToUser,firstName);
        return users.get(0);
    }

    private User mapRowToUser(ResultSet rs, int rowNum)
            throws SQLException {
        return new User(
                rs.getInt("id"),
                rs.getString("firstName"),
                rs.getString("lastName")
                );
    }
*/

}
