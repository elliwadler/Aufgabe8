package com.example.aufgabe8_wadler;
import com.example.aufgabe8_wadler.Tables.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final com.example.aufgabe8_wadler.UserRepository userRepository;

    @Autowired
    public UserService(com.example.aufgabe8_wadler.UserRepository userRepository){
        this.userRepository = userRepository;
    }
    public List<User> hello(){
        return userRepository.findAll();
    }


    public void addNewUser(User user){
        Optional<User> usernameOptional = userRepository.findByUsername(user.getUsername());
        if(usernameOptional.isPresent()){
            throw new IllegalStateException("username taken");
        }
        userRepository.save(user);
    }

    public void deleteStudent(Long id) {
        boolean exists = userRepository.existsById(id);
        if(!exists){
            throw new IllegalStateException(("student with id " +id+ "does not exist"));
        }
        userRepository.deleteById(id);
    }
}
