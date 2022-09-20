package HungerNet.FinalProject.model.entity;

import HungerNet.FinalProject.model.entity.enums.OrderStatusEnum;


import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer orderNumber;

    @Column(name = "order_start_date", nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date orderStartDate;

    @Column(name = "order_end_date")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date stastusUpdatedAt;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatusEnum status;

    @ManyToOne()
    @JoinColumn(name = "orderedBy", referencedColumnName = "id")
    private User orderedBy;


    @ManyToOne
    @JoinColumn(name = "menu_Id", referencedColumnName = "id")
    private Menu menu;

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Date getOrderStartDate() {
        return orderStartDate;
    }

    public void setOrderStartDate(Date orderStartDate) {
        this.orderStartDate = orderStartDate;
    }


    public OrderStatusEnum getStatus() {
        return status;
    }

    public void setStatus(OrderStatusEnum status) {
        this.status = status;
    }

    public User getOrderedBy() {
        return orderedBy;
    }

    public void setOrderedBy(User orderedBy) {
        this.orderedBy = orderedBy;
    }

    public Date getStastusUpdatedAt() {
        return stastusUpdatedAt;
    }

    public void setStastusUpdatedAt(Date stastusUpdatedAt) {
        this.stastusUpdatedAt = stastusUpdatedAt;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }
}
