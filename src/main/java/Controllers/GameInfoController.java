package Controllers;

import Main.GameInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
public class GameInfoController {

    @Autowired
    Main.GameInformationRepository repo;

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

    /**
     * Finds all gameinformation
     * @return all gameinformation
     */
    @RequestMapping("/gameinfo")
    public List<GameInformation> gameInfo() {
        return repo.findAll();
    }

    /**
     * Find one gameinformation by id
     * @param id
     * @return gameinformation of user by id
     */
    @RequestMapping("/gameinfo/{id}")
    public GameInformation gameInformationById(@PathVariable("id") int id){ return repo.findOne(id); }

    /**
     * Finds all the users that have more money than 'Amount'
     * @param amount
     * @return list of gameinformation
     */
    @RequestMapping("/gameinfo/money/more/{amount}")
    public List<GameInformation> gameInforMoney(@PathVariable("amount") int amount){return repo.findByMoneyGreaterThan(amount); }
}
