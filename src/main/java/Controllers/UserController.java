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
    public User saveUser(@RequestParam("firstname") String firstName,
    					 @RequestParam("lastname") String lastName, 
    					 @RequestParam("email") String email,
                         @RequestParam("password") String password,
                         @RequestParam("username") String username
                         ) {
    	User user = new User(firstName, lastName, email, password, username);
    	GameInformation gameInfo = new GameInformation(150, 30, 30, 0, user);
    	user.setGameInformation(gameInfo);
        repo.save(user);
        return user;
    }

    // Finds all users that are in a project by giving the project id
    @RequestMapping("/user/findUsersOfProject/{id}")
    public List<User> findUsers(@PathVariable("id") int id){
        return repo.findByProjects_id(id);
    }

    @RequestMapping("/user/checkpassword/{username}/{password}")
    public Boolean checkPassword(@PathVariable("username") String username,
                                 @PathVariable("password") String password){
        User user = repo.findByUsername(username);
        if(user.getPassword().equals(password)){
            return true;
        } else {
            return false;
        }
    }
}