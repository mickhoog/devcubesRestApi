package Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Main.GameInformation;
@RestController
public class GameInfoController {

	@Autowired
	Main.GameInformationRepository repo;

	@RequestMapping("gameinfo/{id}/{property}/{kind}/{amount}")
	public GameInformation changeInformation(@PathVariable("id") int id,
											 @PathVariable("property") String property,
											 @PathVariable("kind") String kind,
											 @PathVariable("amount") int amount){

		return repo.findOne(id);
	}

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
