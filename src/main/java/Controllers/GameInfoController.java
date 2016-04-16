package Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameInfoController {

	@Autowired
	Main.GameInfoRepository repo;

}
