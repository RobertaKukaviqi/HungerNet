package HungerNet.FinalProject.model.dto;


import HungerNet.FinalProject.model.entity.Restaurant;


import java.util.Date;
import java.util.HashSet;

import java.util.Set;
import java.util.stream.Collectors;

public class RestaurantDto {

    private Integer id;


    private String name;


    private String description;


    private String address;


    private Date dateCreated;

    private Set<MenuDto> menus;

    private Date dateModified;

    MenuDto menuDto = new MenuDto();

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public Set<MenuDto> getMenus() {
        if (menus == null) {
            menus = new HashSet<>();
        }
        return menus;
    }

    public RestaurantDto convertRestaurantToDto(Restaurant restaurant) {


        RestaurantDto restaurantDto = new RestaurantDto();

        restaurantDto.getMenus().addAll(restaurant.getMenus().stream()
                .map(menu -> menuDto.convertMenuToDto(menu))
                .collect(Collectors.toList()));

        restaurantDto.setId(restaurant.getId());
        restaurantDto.setAddress(restaurant.getAddress());
        restaurantDto.setDateCreated(restaurant.getDateCreated());
        restaurantDto.setName(restaurant.getName());
        restaurantDto.setDescription(restaurant.getDescription());


        return restaurantDto;
    }

}
