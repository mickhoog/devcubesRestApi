package Controllers;

import Main.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    Main.UserRepository UserRepository;

    // Get all users
    @RequestMapping("/user")
    public List<User> user() {
        return UserRepository.findAll();
    }

    // Get user by id
    @RequestMapping("/user/{id}")
    public User user(@PathVariable("id") int id) {
        return UserRepository.findOne(id);
    }
    
    @RequestMapping("/user/create")
    public void saveUser(@RequestParam("firstname") String firstName, 
    					 @RequestParam("lastname") String lastName, 
    					 @RequestParam("email") String email) {
    	User u = new User(firstName, lastName, email);
    	UserRepository.save(u);   	
    }
}