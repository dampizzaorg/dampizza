/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dampizza.logic.io;

import com.dampizza.exception.user.UserCreateException;
import com.dampizza.exception.user.UserDeleteException;
import com.dampizza.exception.user.UserQueryException;
import com.dampizza.exception.user.UserUpdateException;
import com.dampizza.logic.dto.UserDTO;
import com.dampizza.model.entity.UserEntity;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * User Manager Interface.
 * CRUD methods for User and Credential.
 * 
 * @author Carlos Santos
 */
public interface UserManagerInterface {

    /**
     * Create user Creates user from userDTO object and password, at the same
     * time a new credential is created with the user data.
     *
     * @param user UserDTO Object
     * @param password Password String
     * @return Success: 1, Already Exists:2, Error:0
     */
    public Integer createUser(UserDTO user, String password) throws UserCreateException, UserQueryException;
    
    /**
     * Create user Creates user from userDTO object, password and type at the same
     * time a new credential is created with the user data.
     *
     * @param user UserDTO Object
     * @param password Password String
     * @return Success: 1, Already Exists:2, Error:0
     */
    public Integer createUser(UserDTO user, String password, Integer type) throws UserCreateException, UserQueryException;
    
    /**
     * Update user
     * @param user UserDTO Object with modified data.
     * @return Success: 1, Doesn't Exists:2, Error:0
     */
    public Integer updateUser(UserDTO user) throws UserUpdateException;
    
    /**
     * Set Active status
     * @param id user id
     * @param value true or false
     * @return
     * @throws UserUpdateException
     * @throws UserQueryException 
     */
    public Integer setActive(Long id, Boolean value) throws UserUpdateException, UserQueryException;
    
    /**
     * Delete user
     * @param id user id.
     * @return Success: 1, Doesn't Exists:2, Error:0
     */
    public Integer deleteUser(Long id) throws UserDeleteException;
    
    /**
     * Get all users
     * @return List of UserDTO
     * @throws UserQueryException 
     */
    public List<UserDTO> getAllUsers() throws UserQueryException;
    
    /**
     * Get user by username
     * @param username username
     * @return User with matching id.
     * @throws UserQueryException 
     */
    public UserDTO getUserByUsername(String username) throws UserQueryException;
    
    /**
     * Get user entity by username
     * @param username username
     * @return User with matching username
     * @throws UserQueryException 
     */
    public UserEntity getUserEntityByUsername(String username) throws UserQueryException;
    
    /**
     * Get user entity by id
     * @param id user id
     * @return user with matching id
     * @throws UserQueryException 
     */
    public UserEntity getUserEntityById(Long id) throws UserQueryException;
    
    /**
     * Get user entity by username
     * @param type
     * @return UserDTO List
     * @throws UserQueryException
     */
    public List<UserDTO> getUsersByType(Integer type) throws UserQueryException;
    
    /**
     * Check if user is already on the database.
     * @param username
     * @return 1 yes, 2 no, 0 error.
     * @throws UserQueryException 
     */
    public Integer userExists(String username) throws UserQueryException;
    
    
    

    // CREDENTIAL METHODS
    /**
     * Create credential.
     * This method is always called when a user is created.
     * @param user UserEntity
     * @param username username
     * @param password password
     */
    public void createCredential(UserEntity user, String username, String password, Integer type);
    
    /**
     * Check credential.
     * Searchs for a record where the username and password are matched.
     * @param username
     * @param password
     * @return 1 yes, 2 no, 0 error. 
     */
    public Integer checkCredential(String username, String password);

    public Integer checkStatus(String username);
    //public Boolean userExist(String user);
    /**
     * Change password
     * @param criteria
     * @param password
     * @return 1 Success, 2 user not found, 0 error.
     */
    public Integer changePassword(String criteria, String password);
    /**
     * Check email exist in the DB
     * @return 1 Exist, 2 email not found, 0 error
     */
    public Integer emailExist(String email)throws UserQueryException;

    /**
     * Returns session
     * @return 
     */
    public HashMap getSession();

    /**
     * Reset shopping cart
     */
    public void resetCart() throws UserQueryException;
    
    

}
