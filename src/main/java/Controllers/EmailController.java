package Controllers;

import Main.Email;
import Main.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class EmailController {

    @Autowired
    Main.UserRepository userRepo;

    @Autowired
    Main.EmailRepository emailRepo;

    @RequestMapping(value = "/email/new")
    public Email createEmail(@RequestParam("salary") Double salary,
                             @RequestParam("userId") int userId){

        Email email = new Email();

        User user = userRepo.getOne(userId);

        Date date = new Date();
        long datetime = date.getTime();

        email.setUserId(userId);
        email.setDate(datetime);
        email.setDescription("Beste " + user.getUsername() + ", \n\n" +
                "U heeft: " + salary + " dollar verdient! \n\n" +
                "Met vriendelijke groet, \nhet DevCubes team.");
        email.setSender("Salary team");
        email.setSubject("Salary: "  +  datetime);

        emailRepo.save(email);

        return email;
    }

    @RequestMapping("/email/{id}")
    public List<Email> findEmailsId(@PathVariable("id") int id){
        List<Email> emails = emailRepo.findByUserId(id);
        return emails;
    }
}
