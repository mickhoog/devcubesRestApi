package Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Main.GameInformation;

@RestController
public class GameInfoController {

	@Autowired
	Main.GameInfoRepository repo;

	@RequestMapping("/gameinfo/update/{id}")
	public void update(@PathVariable("id") int id, 
					   @RequestParam(value="likeability", required=false) Integer likeability,
					   @RequestParam(value="money", required=false) Integer money,
					   @RequestParam(value="pclevel", required=false) Integer pcLevel,
					   @RequestParam(value="productivity", required=false) Integer productivity) {
		GameInformation gameInfo = repo.findOne(id);
		if(gameInfo != null) {
			if(likeability != null)
				gameInfo.setLikeability(likeability);
			if(money != null)
				gameInfo.setMoney(money);				
			if(productivity != null)
				gameInfo.setProductivity(productivity);
			if(pcLevel != null)
				gameInfo.setPc_level(pcLevel);
			repo.save(gameInfo);	
		}
	}
	
	@RequestMapping("/gameinfo")
	public List<GameInformation> gameInfo() {
		return repo.findAll();
	}
}
