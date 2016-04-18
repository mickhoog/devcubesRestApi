package Controllers;

import Main.GameInformation;
import Main.Project;
import Main.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
public class ProjectController {

    @Autowired
    Main.ProjectRepository repo;

    @Autowired
    Main.UserRepository userRepository;

    // Get all users
    @RequestMapping("/project")
    public List<Project> books() {
        return repo.findAll();
    }

    // Get user by id
    @RequestMapping("/project/{id}")
    public Project project(@PathVariable("id") int id) {
        return repo.findOne(id);
    }

    // Create new project, given only a name
    @RequestMapping("/project/create")
    public Project newProject(@RequestParam("name") String name) {
        Project newProject = new Project(name);
        repo.save(newProject);
        return newProject;
    }


    // Takes projectId & userId then adds the project to an user and the user to a project.
    // Return the project with the newly added user.
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
}
