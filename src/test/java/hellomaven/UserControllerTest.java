package hellomaven;

import java.awt.List;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
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
	private User user2;
	private MockMvc mockMvc;
	
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
		User user2 = new User();
		user2.setId(2);
		user2.setPassword("secretPass");
		user2.setFirst_name("john");
		user2.setLast_name("secondUser");
		user2.setUsername("john");
		user2.setEmail("john@hotmail.com");
		this.user2 = user2;
		ArrayList<User> l = new ArrayList<User>();
		l.add(user);
		l.add(user2);
		mockMvc = MockMvcBuilders.standaloneSetup(this.userController).build();
		Mockito.when(userController.user()).thenReturn(l);
		Mockito.when(this.userController.saveUser(user.getFirst_name(), user.getLast_name(), user.getEmail(), user.getPassword(), user.getUsername())).thenReturn(this.user);		
	}
	
	@Test
	public void testCreateUserMustCreateUser() throws Exception {
		mockMvc.perform(post("/user/create").param("firstname", "eddy").param("lastname", "murphy").param("email", "e@hotmail.com").param("password", "123pass").param("username", "eddy"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.first_name", is("eddy")));		
	}
	
	@Test
	public void testUsersMustReturnAllUsers() throws Exception {
		mockMvc.perform(get("/user"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(2)))
		.andExpect(jsonPath("$[0].id", is(1)))
		.andExpect(jsonPath("$[0].email", is(user.getEmail())))
		.andExpect(jsonPath("$[0].first_name", is(user.getFirst_name())))
		.andExpect(jsonPath("$[1].id", is(2)))
		.andExpect(jsonPath("$[1].email", is(user2.getEmail())))
		.andExpect(jsonPath("$[1].first_name", is(user2.getFirst_name())));
		
		
	}
}
