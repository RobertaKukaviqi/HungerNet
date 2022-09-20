package HungerNet.FinalProject.model.dto;


import HungerNet.FinalProject.model.entity.Menu;
import com.fasterxml.jackson.annotation.JsonIgnore;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class MenuDto {

    private Integer id;


    private String name;
    private String description;

    private Integer startDate;

    private Integer endDate;
    private Date dateCreated;

    private Set<ItemDto> items;
    private Date dateModified;

    private Integer restaurant;

    public Integer getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Integer restaurant) {
        this.restaurant = restaurant;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public Integer getStartDate() {
        return startDate;
    }

    public void setStartDate(Integer startDate) {
        this.startDate = startDate;
    }

    public Integer getEndDate() {
        return endDate;
    }

    public void setEndDate(Integer endDate) {
        this.endDate = endDate;
    }

    public Set<ItemDto> getItems() {
        if (items == null) {
            items = new HashSet<>();
        }
        return items;
    }

    public MenuDto convertMenuToDto(Menu menu) {


        ItemDto itemDto = new ItemDto();
        MenuDto menuDto = new MenuDto();

        menuDto.getItems().addAll(menu.getItems().stream()
                .map(itemDto::convertItemToDto)
                .collect(Collectors.toList()));
        menuDto.setId(menu.getId());
        menuDto.setDateCreated(menu.getDateCreated());
        menuDto.setDateModified(menu.getDateModified());
        menuDto.setName(menu.getName());
        menuDto.setDescription(menu.getDescription());
        menuDto.setRestaurant(menu.getRestaurant().getId());

        return menuDto;
    }

    public Menu convertDtoToMenu(MenuDto menuDto) {

        Menu menu = new Menu();
        menu.setName(menuDto.getName());
        menu.setDescription(menuDto.getDescription());
        menu.setStartDate(menuDto.getStartDate());
        menu.setEndDate(menuDto.getEndDate());
        return menu;
    }

}
