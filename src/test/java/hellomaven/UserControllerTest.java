package hellomaven;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import Controllers.UserController;
import Main.User;
import Main.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class UserControllerTest {
	
	
	@Configuration
	static class UserControllerTestConfiguration {
	
	@Bean 
	public UserRepository userRepository() {
		return Mockito.mock(UserRepository.class);
	}
	
	@Bean 
	public UserController userController() {
		return new UserController();
	}
	}
	
	@Autowired
	private UserController userController;
	
	@Autowired 
	private UserRepository userRepository;
	
	
	private User user;
	
	@Before
	public void setup() throws Exception {
		User user = new User();
		user.setId(1);
		user.setPassword("123pass");
		user.setFirst_name("ëddy");
		user.setLast_name("murphy");
		user.setUsername("eddy");
		user.setEmail("e@hotmail.com");
		
		this.user = user;
		
		Mockito.when(this.userController.saveUser(user.getFirst_name(), user.getLast_name(), user.getEmail(), user.getPassword(), user.getUsername())).thenReturn(this.user);
		Mockito.when(this.userController.findUserByEmail(user.getEmail())).thenReturn(this.user);
		
	}
	
	@Test
	public void testCreateUser() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(this.userController).build();
		mockMvc.perform(post("/user/create").param("firstname", "eddy").param("lastname", "murphy").param("email", "e@hotmail.com").param("password", "123pass").param("username", "eddy"))
				.andExpect(status().isOk());		
	}
	
	@Test
	public void testFindUserByEmail() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(this.userController).build();
		mockMvc.perform(get("/user/email/e@hotmail.com"))
		.andExpect(status().isOk());
		
		
	}
}
