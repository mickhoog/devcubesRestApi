package Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import Main.GameInformation;
@RestController
public class GameInfoController {

    @Autowired
    Main.GameInformationRepository repo;

    //
    @RequestMapping(value = "gameinfo/{id}/{property}/{kind}/{amount}")
    public GameInformation changeInformation(@PathVariable("id") int id,
                                             @PathVariable("property") String property,
                                             @PathVariable("kind") String kind,
                                             @PathVariable("amount") int amount){
        GameInformation current = repo.findOne(id);
        switch (property) {
            case "money":
                int newMoney = current.changeProperty(current.getMoney(), kind, amount);
                current.setMoney(newMoney);
                break;
            case "productivity":
                int newProductivity = current.changeProperty(current.getProductivity(), kind, amount);
                current.setProductivity(newProductivity);
                break;
            case "likeability":
                int newLikeablity = current.changeProperty(current.getLikeability(), kind, amount);
                current.setLikeability(newLikeablity);
                break;
            default:
                break;
        }
        repo.save(current);
        return current;
    }

    // Upgrade pc
    //RequestMapping("/gameinfo/upgrade/pc")

    // Finds all gameinformation
    @RequestMapping("/gameinfo")
    public List<GameInformation> gameInfo() {
        return repo.findAll();
    }

    // Find gameinformation by id
    @RequestMapping("/gameinfo/{id}")
    public GameInformation gameInformationById(@PathVariable("id") int id){ return repo.findOne(id); }

    // Finds all the users that have more money than 'Amount'
    @RequestMapping("/gameinfo/money/more/{amount}")
    public List<GameInformation> gameInforMoney(@PathVariable("amount") int amount){return repo.findByMoneyGreaterThan(amount); }
}
