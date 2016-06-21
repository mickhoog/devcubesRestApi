package Controllers;

import Main.SonarPush;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Controller
public class GraphController {

    @Autowired
    Main.SonarPushRepository sonarRepo;

    private static final Logger log = LoggerFactory.getLogger(GraphController.class);

    @RequestMapping("/salarygraph")
    public String salarygraph(@RequestParam(value="id", required=true) int id, Model model) {
        //List<SonarPush> sonarPushList = sonarRepo.findTop3ByUser_idOrderByDateDesc(id); // Get top 3
        List<SonarPush> sonarPushList = sonarRepo.findByUser_idOrderByDateDesc(id);

        List<Double> salaries = new ArrayList<>();
        List<Long> timestamps = new ArrayList<>();
        for (SonarPush sp : sonarPushList){
            log.info(String.valueOf(sp.getId()));
            salaries.add(sp.getSalary());
            timestamps.add(sp.getDate().getTime());
        }

        //model.addAttribute("amountSonarpushes", sonarPushList.size());
        model.addAttribute("salaries", salaries);
        model.addAttribute("timestamps", timestamps );

        return "salarygraph";
    }
}
