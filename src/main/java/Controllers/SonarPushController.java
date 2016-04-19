package Controllers;

import Main.SonarPush;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Mick on 19-4-2016.
 */
@RestController
public class SonarPushController {
    @Autowired
    Main.SonarPushRepository repo;

    // Get all users
    @RequestMapping("/sonarpush")
    public List<SonarPush> user() {
        return repo.findAll();
    }
}
