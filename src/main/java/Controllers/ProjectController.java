package Controllers;

import Main.HttpConnector;
import Main.Project;
import Main.SonarPush;
import Main.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
@RestController
public class ProjectController {

    @Autowired
    Main.ProjectRepository repo;

    @Autowired
    Main.UserRepository userRepository;
    
    @Autowired
    Main.SonarPushRepository sonarRepo;
    
    private final String sonarDeleteUrl = "http://localhost:8080/sonarpush/delete";
    
    // Get all users

    /**
     * Get all users
     * @return list of all users
     */
    @RequestMapping("/project")
    public List<Project> books() {
        return repo.findAll();
    }

    /**
     * Get user by id
     * @param id
     * @return project found by id
     */
    @RequestMapping("/project/{id}")
    public Project project(@PathVariable("id") int id) {
        return repo.findOne(id);
    }


    /**
     * Create new project, given a name, description, date, sonarname
     * @param name
     * @param description
     * @param startDate
     * @param sonarName
     * @return a newly made project
     * @throws ParseException
     */
    @RequestMapping("/project/create")
    public Project newProject(@RequestParam("name") String name,
                              @RequestParam("description") String description,
                              @RequestParam("startdate") String startDate,
                              @RequestParam("sonarName") String sonarName
                              ) throws ParseException {

        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date date = DATE_FORMAT.parse(startDate);
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        Project newProject = new Project(name, description, sqlDate, sonarName);
        repo.save(newProject);
        return newProject;
    }

    /**
     * Takes projectId & userId then adds the project to an user and the user to a project
     * @param projectId
     * @param userId
     * @return the project with the newly added user
     */
    @RequestMapping("/project/addUser")
    public Project addUser(@RequestParam("projectId") int projectId,
                           @RequestParam("userId") int userId){

        Project project = repo.findOne(projectId);
        User user = userRepository.findOne(userId);

        project.addUser(user);
        user.addProject(project);

        project = repo.save(project);
        user = userRepository.save(user);

        return project;
    }

    /**
     * Deletes a project from the database
     * @param projectId
     * @return String that its done
     */
    @RequestMapping("/project/delete")
    public String deleteProject(@RequestParam("projectId") int projectId) {
    	Project project = repo.findOne(projectId);
    	//alle sonarpushes ontkoppelen
    	HttpConnector hp = new HttpConnector("user", "c1cc6367-5c10-4d76-955e-d58678eeb1f8");
    	List<SonarPush> sonarPushes = project.getSonarPushes();
    	for(int i = 0; i < sonarPushes.size(); i++) {
    		SonarPush s = sonarPushes.get(i);
    		hp.sendPost(sonarDeleteUrl, "sonarPushId="+s.getId(), true);
    	}
    	//alle users ontkoppelen
    	userRepository.delete(project.getUsers());
    	repo.delete(projectId);
    	return "Project " + project.getName() + " has been deleted.";
    }

    /**
     * Removes a user from a project
     * @param projectId
     * @param userId
     * @return String that its done
     */
    @RequestMapping("project/deleteUser")
    public String deleteUserFromProject(@RequestParam("projectId") int projectId,
            @RequestParam("userId") int userId) {
    	Project project = repo.findOne(projectId);
    	User user = userRepository.findOne(userId);
    	project.getUsers().remove(user);
    	user.getProjects().remove(project);
    	userRepository.save(user);
    	repo.save(project);
    	return user.getFirst_name() + " is not a part of the project " + project.getName() + " anymore.";
    }
}
