package Controllers;

import Main.Project;
import Main.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
@ComponentScan(basePackages = {"Models"})
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


    // Create new project, given a name, description and date
    @RequestMapping("/project/create")
    public Project newProject(@RequestParam("name") String name,
                              @RequestParam("description") String description,
                              @RequestParam("startdate") String startDate) throws ParseException {

        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date date = DATE_FORMAT.parse(startDate);
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        Project newProject = new Project(name, description, sqlDate);
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
