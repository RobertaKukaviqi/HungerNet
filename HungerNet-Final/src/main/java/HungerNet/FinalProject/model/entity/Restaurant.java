package HungerNet.FinalProject.model.entity;

import javax.persistence.*;
import java.util.*;


@Entity
@Table(name = "restaurant")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "address", nullable = false)
    private String address;


    @Column(name = "date_created", nullable = false)
    @Temporal(value = TemporalType.DATE)
    private Date dateCreated;

    @Column(name = "date_modified")
    @Temporal(value = TemporalType.DATE)
    private Date dateModified;


    @OneToMany(mappedBy = "restaurant")
    private Set<Menu> menus;

    @ManyToMany(mappedBy = "restaurants")
    private List<User> users;


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public Set<Menu> getMenus() {
        if (menus == null) {
            menus = new HashSet<>();
        }
        return menus;
    }

    public List<User> getUser() {
        if (users == null) {
            users = new ArrayList<>();
        }
        return users;
    }
}
