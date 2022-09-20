package HungerNet.FinalProject;

import HungerNet.FinalProject.controllers.MenuController;
import HungerNet.FinalProject.model.entity.Menu;
import HungerNet.FinalProject.model.entity.Restaurant;
import HungerNet.FinalProject.service.MenuService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
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
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class MenuControllerTest {

  @Mock
  MenuService menuService;

  @InjectMocks
  MenuController menuController;

  private MockMvc mockMvc;

  @Before
  public void setUp(){
    MockitoAnnotations.initMocks(FinalProjectApplicationTests.class);
    mockMvc= MockMvcBuilders.standaloneSetup(menuController).build();
  }

  @Test
  public void getMenu() throws Exception{
    Menu menu = new Menu();
    Restaurant restaurant = new Restaurant();
    restaurant.setId(1);
    menu.setId(1);
    menu.setDescription("test menu");
    menu.setRestaurant(restaurant);

    List<Menu> menus = new ArrayList<>();
    menus.add(menu);
    when(menuService.findMenuByRestaurantId(1)).thenReturn(menus);

    mockMvc.perform(get("/restaurant/1/menu/all"))
        .andExpect(status().isOk()).andExpect(jsonPath("$[0].id", is(1)));
  }

//  @Test
//  public void createMenu() throws Exception{
//    String exampleMenu = "{\n\"name\" : \"Breakfast\",\n\"description\" : \"Breakfast Menu\",\n\"startDate\" : \"1\",\n\"endDate\" : \"15\",\n\"restaurant\" : 1\n}";
//    RequestBuilder requestBuilder = MockMvcRequestBuilders
//        .post("/menu/register")
//        .accept(MediaType.APPLICATION_JSON).content(exampleMenu)
//        .contentType(MediaType.APPLICATION_JSON);
//
//    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//
//    MockHttpServletResponse response = result.getResponse();
//
//    assertEquals(response.getContentAsString(), "");
//  }
}
