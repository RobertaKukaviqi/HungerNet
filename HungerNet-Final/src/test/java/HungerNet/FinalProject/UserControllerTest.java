package HungerNet.FinalProject;

import HungerNet.FinalProject.controllers.UserController;
import HungerNet.FinalProject.model.entity.Role;
import HungerNet.FinalProject.model.entity.User;
import HungerNet.FinalProject.model.entity.UserDetails;
import HungerNet.FinalProject.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class UserControllerTest {
  @Mock
  private UserService userService;

  @InjectMocks
  private UserController userController;

  private MockMvc mockMvc;

  @Before
  public void setUp(){
    MockitoAnnotations.initMocks(FinalProjectApplicationTests.class);
    mockMvc= MockMvcBuilders.standaloneSetup(userController).build();
  }

  //register client
  @Test
  public void createClientUser() throws Exception {
    String exampleClientUser = "{\n\"username\" : \"Ermal\",\n\"password\": \"1\",\n\"roles\" : [3],\n\"enabled\" : 1,\n\"firstName\" : \"ere444444r\",\n\"lastName\": \"kfkf\",\n\"phoneNumber\" : \"2323\",\n\"isNonLocked\" : 1,\n\"email\": \"233\",\n\"restaurants\" : []\n}";

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .post("/user/register")
        .accept(MediaType.APPLICATION_JSON).content(exampleClientUser)
        .contentType(MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();

    MockHttpServletResponse response = result.getResponse();

    assertEquals(HttpStatus.OK.value(), response.getStatus());
  }

  //manager without restaurant error
  @Test
  public void createManagerUser() throws Exception {
    String exampleManager = "{\n\"username\" : \"Ermal\",\n\"password\": \"1\",\n\"roles\" : [2],\n\"enabled\" : 1,\n\"firstName\" : \"ere444444r\",\n\"lastName\": \"kfkf\",\n\"phoneNumber\" : \"2323\",\n\"isNonLocked\" : 1,\n\"email\": \"233\",\n\"restaurants\" : []\n}";

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .post("/user/register")
        .accept(MediaType.APPLICATION_JSON).content(exampleManager)
        .contentType(MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();

    MockHttpServletResponse response = result.getResponse();

    assertEquals(response.getContentAsString(), "{\"message\":\"Restaurant manager should be assigned to at least 1 existing restaurant\"}");
  }

  //get user by id
  @Test
  public void testUsers() throws Exception{
    List<User> userList = new ArrayList<>();
    User user1 = new User();
    user1.setUsername("dddd");
    user1.setPassword("1");
    user1.setId(1);
    user1.setEnabled(true);
    user1.setAccountNonLocked(true);

    UserDetails userDetails = new UserDetails();
    userDetails.setFirstName("Ermal");
    userDetails.setEmail("fsfd");
    userDetails.setLastName("322");
    userDetails.setPhoneNumber("23232");
    user1.setCreatedBy(new User());
    user1.setModifiedBy(new User());

    Set<Role> roles = new HashSet<>();
    Role role = new Role();
    role.setId(3);
    roles.add(role);

    user1.setUserDetails(userDetails);

    userList.add(user1);

    when(userService.findById(1)).thenReturn(userList.stream().findFirst());
    assertEquals(userService.findById(1).get(), user1);


    mockMvc.perform(get("/user/1"))
          .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].firstName", is("Ermal")));
  }

}
