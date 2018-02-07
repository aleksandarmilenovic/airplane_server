package org.plu.rest.controllers;


import org.plu.dao.UserRepository;
import org.plu.entities.Note;
import org.plu.entities.Privilegues;
import org.plu.entities.User;
import org.plu.rest.services.MailSender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
@RestController
@RequestMapping("/user")
public class UserControler {

    @Autowired
    private UserRepository userRepository;



    @GetMapping("/all")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/new/{firstname}/{lastname}/{username}/{password}/{privilegues}")
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
    @GetMapping("/sendMail/{firstname}/{lastname}/{username}/{password}")
    public String addsNew(@PathVariable(value = "firstname") String firstname,@PathVariable(value = "lastname") String lastname,
                         @PathVariable(value = "username") String username,@PathVariable(value = "password") String password) {

        MailSender mailSender = new MailSender();
        mailSender.sendMail(username,password,firstname,lastname);

        return "SENT";

    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<Note> deleteUser(@PathVariable(value = "id") int id){
        User deleteUser = userRepository.findOne(id);

        if(deleteUser == null){
            return ResponseEntity.notFound().build();
        }

        if(deleteUser.getUsername().equalsIgnoreCase("ADMIN")){
            return ResponseEntity.notFound().build();
        }

        userRepository.delete(deleteUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/changepassword/{id}/{password}")
    public ResponseEntity<User> changePassword(@PathVariable(value = "id") int id,@PathVariable(value = "password") String password){

        User user = userRepository.findOne(id);

        if(user == null){
            return ResponseEntity.notFound().build();
        }
        user.setPassword(password);
        User changePassUser = userRepository.save(user);
        return ResponseEntity.ok(changePassUser);

    }

    @GetMapping("/checkjmbg/{jmbg}")
    public static String isJMBGOk(@PathVariable(value = "jmbg") String jmbg) {
        if(jmbg.length() != 13){
            return  "false";
        }
        String s = "";
        String input = jmbg.substring(0, jmbg.length() - 1);
        int i = 0, i1 = 0, diff = 0;
        i = i + (Integer.valueOf(String.valueOf(jmbg.charAt(0))).intValue() * 7);
        i = i + (Integer.valueOf(String.valueOf(jmbg.charAt(1))).intValue() * 6);
        i = i + (Integer.valueOf(String.valueOf(jmbg.charAt(2))).intValue() * 5);
        i = i + (Integer.valueOf(String.valueOf(jmbg.charAt(3))).intValue() * 4);
        i = i + (Integer.valueOf(String.valueOf(jmbg.charAt(4))).intValue() * 3);
        i = i + (Integer.valueOf(String.valueOf(jmbg.charAt(5))).intValue() * 2);
        i = i + (Integer.valueOf(String.valueOf(jmbg.charAt(6))).intValue() * 7);
        i = i + (Integer.valueOf(String.valueOf(jmbg.charAt(7))).intValue() * 6);
        i = i + (Integer.valueOf(String.valueOf(jmbg.charAt(8))).intValue() * 5);
        i = i + (Integer.valueOf(String.valueOf(jmbg.charAt(9))).intValue() * 4);
        i = i + (Integer.valueOf(String.valueOf(jmbg.charAt(10))).intValue() * 3);
        i = i + (Integer.valueOf(String.valueOf(jmbg.charAt(11))).intValue() * 2);
        i1 = i;
        i = i / 11;
        diff = i1 - (i * 11);
        if ((diff == 0) || (diff == 1)) {
            s = input + 0;
        } else {
            s = input + (11 - diff);
        }
        //return s.equals(jmbg);
        if(s.equals(jmbg)){
            return "true";
        }else{
            return "false";
        }
    }

    @GetMapping("/checkcard/{card}")
    public String check(@PathVariable(value = "card") String card){
        int s1 = 0;
        int s2 = 0;
        int s3 = 0;
        char[] niz = card.toCharArray();
        for(int i = 0;i<niz.length;i++) {
            if(i % 2 == 0) {
                s2 = Integer.parseInt(""+niz[i])*2+ s2;
                s3 = Integer.parseInt(""+niz[i])+s3;
            }else {
                s1 = s1 + Integer.parseInt(""+niz[i])*2;
            }
        }

        if(s2 > 9) {
            s1 = s1+s2;
        }else {
            s1 = s1+s3;
        }

        if(s1 % 10 == 0) {
            return "true";
        }
        return "false";

    }

    }

