package Controllers;

import Main.Issue;
import Main.SonarPush;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SonarPushController {
    @Autowired
    static
    Main.SonarPushRepository repo;

    // Get all users
    @RequestMapping("/sonarpush")
    public List<SonarPush> getAll() {
        return repo.findAll();
    }

    @RequestMapping("/sonarpush/{id}")
    public SonarPush getSonarPush(@PathVariable("id") int id) {
        return repo.findOne(id);
    }

    public static List<Issue> getIssuesCont(int id){
        return repo.getOne(id).getIssues();
    }
}
