package HungerNet.FinalProject.model.entity;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "price", nullable = false)
    private Double price;

    @OneToOne
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    private User createdBy;

    @OneToOne
    @JoinColumn(name = "modified_by", referencedColumnName = "id")
    private User modifiedBy;

    @Column(name = "date_created", nullable = false)
    @Temporal(value = TemporalType.DATE)
    private java.util.Date dateCreated;

    @Column(name = "date_modified", nullable = false)
    private java.util.Date dateModified;

    @ManyToOne()
    @JoinColumn(
            name = "menu_id",
            referencedColumnName = "id"
    )
    private Menu menu;

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public User getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(User modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public java.util.Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(java.util.Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public java.util.Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public HungerNet.FinalProject.model.entity.Menu getMenu() {
        return menu;
    }

    public void setMenu(HungerNet.FinalProject.model.entity.Menu menu) {
        this.menu = menu;
    }
}
