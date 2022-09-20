package HungerNet.FinalProject.model.entity;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "menu")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "start_date", nullable = false)
    private Integer startDate;

    @Column(name = "end_date", nullable = false)
    private Integer endDate;

    @Column(name = "date_created", nullable = false)
    @Temporal(value = TemporalType.DATE)
    private Date dateCreated;

    @Column(name = "date_modified", nullable = false)
    @Temporal(value = TemporalType.DATE)
    private Date dateModified;

    @ManyToOne()
    @JoinColumn(
            name = "restaurant_id",
            referencedColumnName = "id"
    )
    private Restaurant restaurant;

    @OneToMany(mappedBy = "menu")
    private Set<Item> items;

    @OneToMany(mappedBy = "menu")
    private List<Orders> order;


    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
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

    public Set<Item> getItems() {
        if (items == null) {
            items = new HashSet<>();
        }
        return items;
    }
}
