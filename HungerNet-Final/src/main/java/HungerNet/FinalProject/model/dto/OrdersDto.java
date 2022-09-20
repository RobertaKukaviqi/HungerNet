package HungerNet.FinalProject.model.dto;


import HungerNet.FinalProject.model.entity.Orders;
import HungerNet.FinalProject.model.entity.enums.OrderStatusEnum;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

public class OrdersDto {

    private Integer orderNumber;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date dateCreated;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date statusUpdatedAt;

    private OrderStatusEnum status;

    public Integer getOrderNumber() {
        return orderNumber;
    }

    private String restaurant;

    private String orderedBy;
    private String menu;

    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public String getOrderedBy() {
        return orderedBy;
    }

    public void setOrderedBy(String orderedBy) {
        this.orderedBy = orderedBy;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public OrderStatusEnum getStatus() {
        return status;
    }

    public void setStatus(OrderStatusEnum status) {
        this.status = status;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public Date getStatusUpdatedAt() {
        return statusUpdatedAt;
    }

    public void setStatusUpdatedAt(Date statusUpdatedAt) {
        this.statusUpdatedAt = statusUpdatedAt;
    }

    public OrdersDto convertItemToDto(Orders orders) {

        OrdersDto ordersDto = new OrdersDto();

        ordersDto.setOrderNumber(orders.getOrderNumber());
        ordersDto.setStatus(orders.getStatus());
        ordersDto.setDateCreated(orders.getOrderStartDate());
        ordersDto.setStatusUpdatedAt(orders.getStastusUpdatedAt());
        ordersDto.setMenu(orders.getMenu().getName());
        ordersDto.setRestaurant(orders.getMenu().getRestaurant().getName());
        ordersDto.setOrderedBy(orders.getOrderedBy().getUsername());
        return ordersDto;
    }


}
