package Controllers;

import Main.GameInformation;
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
    Main.UserRepository repo;
    
    // Get all users
    @RequestMapping("/user")
    public List<User> user() {
        return repo.findAll();
    }

    // Get user by id
    @RequestMapping("/user/{id}")
    public User user(@PathVariable("id") int id) {
        return repo.findOne(id);
    }
    
    // Create an user
    @RequestMapping("/user/create")
    public void saveUser(@RequestParam("firstname") String firstName, 
    					 @RequestParam("lastname") String lastName, 
    					 @RequestParam("email") String email) {
    	User user = new User(firstName, lastName, email);
    	GameInformation gameInfo = new GameInformation(0,0,0,0, user);
    	user.setGameInformation(gameInfo);
        repo.save(user);
    }

    // Finds all users that are in a project by giving the project id
    @RequestMapping("/user/findUsersOfProject/{id}")
    public List<User> findUsers(@PathVariable("id") int id){
        return repo.findByProjects_id(id);
    }
    
    
}