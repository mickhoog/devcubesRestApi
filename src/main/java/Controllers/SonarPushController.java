package Controllers;

import Main.SonarPush;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SonarPushController {
    @Autowired
    Main.SonarPushRepository repo;
    
    @Autowired 
    Main.IssueRepository issueRepo;

    // Get all users
    @RequestMapping("/sonarpush")
    public List<SonarPush> getAll() {
        return repo.findAll();
    }

    @RequestMapping("/sonarpush/{id}")
    public SonarPush getSonarPush(@PathVariable("id") int id) {
        return repo.findOne(id);
    }

    @RequestMapping("sonarpush/delete")
    public void deleteSonarPush(@RequestParam("sonarPushId") int id) {
    	SonarPush sp = repo.findOne(id);
    	//alle issues eerst ontkoppelen
    	issueRepo.delete(sp.getIssues());
    	repo.delete(sp);
    }
 
    /*
    public static List<Issue> getIssuesCont(int id){
        return repo.getOne(id).getIssues();
    }
    */
}
