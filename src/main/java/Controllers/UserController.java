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

    /**
     * Get all users
     * @return list of all users
     */
    @RequestMapping("/user")
    public List<User> user() {
        return repo.findAll();
    }

    /**
     * Find user by username
     * @param username
     * @return user
     */
    @RequestMapping("/user/username/{username}")
    public int getUserByUsername(@PathVariable("username") String username){
        return repo.findByUsername(username).getId();
    }

    /**
     * Find user by id
     * @param id
     * @return user
     */
    @RequestMapping("/user/{id}")
    public User user(@PathVariable("id") int id) {
        return repo.findOne(id);
    }

    /**
     * Find user by email
     * @param email
     * @return user
     */
    @RequestMapping("user/email/{email}")
    public User findUserByEmail(@PathVariable("email") String email) {
    	return repo.findByEmail(email);
    }

    /**
     * Create user
     * @param firstName
     * @param lastName
     * @param email
     * @param password
     * @param username
     * @return newly created user
     */
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

    /**
     * Delete user from database
     * @param id
     */
    @RequestMapping("/user/delete/{id}")
    public void deleteUser(@PathVariable("id") int id){
        repo.delete(id);
    }

    /**
     * Find all users that are in a project by giving the project id
     * @param id
     * @return list of user
     */
    @RequestMapping("/user/findUsersOfProject/{id}")
    public List<User> findUsers(@PathVariable("id") int id){
        return repo.findByProjects_id(id);
    }

    /**
     * check if given password is the same as the user password
     * @param username
     * @param password
     * @return boolean
     */
    @RequestMapping("/user/checkpassword/{username}/{password}")
    public Boolean checkPassword(@PathVariable("username") String username,
                                 @PathVariable("password") String password){
        User user = repo.findByUsername(username);
        return user.getPassword().equals(password);
    }

    /**
     * Change user password
     * @param userId
     * @param password
     * @return user
     */
    @RequestMapping("/changepassword")
    public User changePassword(@RequestParam("userId") int userId, @RequestParam("password") String password) {
    	User user = repo.findOne(userId);
    	user.setPassword(password);
    	repo.save(user);
    	return user;
    }
    
}