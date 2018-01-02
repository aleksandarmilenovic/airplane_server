package org.plu.rest.controllers;


import org.plu.dao.UserRepository;
import org.plu.entities.Privilegues;
import org.plu.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserControler {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("new/{firstname}/{lastname}/{username}/{password}/{privilegues}")
    public String addNew(@PathVariable(value = "firstname") String firstname,@PathVariable(value = "lastname") String lastname,
                         @PathVariable(value = "username") String username,@PathVariable(value = "password") String password,
                         @PathVariable(value = "privilegues") String privilegues){

        User user;

        if(privilegues.equalsIgnoreCase("ADMIN")){
            user = new User(firstname,lastname,username,password,"ADMIN");
        }else if(privilegues.equalsIgnoreCase("OPERATOR")){
            user = new User(firstname,lastname,username,password,"OPERATOR");
        }else{
            user = new User(firstname,lastname,username,password,"USER");
        }

        if(user!=null){
            userRepository.save(user);
            return "Success!!!";
        }

        return "There was a mistake.Try again later";
    }
}
