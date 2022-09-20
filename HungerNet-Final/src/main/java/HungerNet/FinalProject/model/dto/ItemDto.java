package HungerNet.FinalProject.model.dto;

import HungerNet.FinalProject.model.entity.Item;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

public class ItemDto {

    private Integer id;


    private String name;

    private Double price;
    private String description;


    private Date dateCreated;


    private Date dateModified;

    private Integer menu;


    public Integer getMenu() {
        return menu;
    }

    public void setMenu(Integer menu) {
        this.menu = menu;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public ItemDto convertItemToDto(Item item) {


        ItemDto itemDto = new ItemDto();

        itemDto.setId(item.getId());
        itemDto.setDateCreated(item.getDateCreated());
        itemDto.setDateModified(item.getDateModified());
        itemDto.setName(item.getName());
        itemDto.setDescription(item.getDescription());
        itemDto.setPrice(item.getPrice());
        itemDto.setMenu(item.getMenu().getId());

        return itemDto;
    }

    public Item convertDtoToItem(ItemDto itemDto) {

        Item item = new Item();

        item.setName(itemDto.getName());
        item.setDescription(itemDto.getDescription());
        item.setPrice(itemDto.getPrice());


        return item;
    }

}
