package HungerNet.FinalProject.model.dto;

import HungerNet.FinalProject.model.entity.User;
import HungerNet.FinalProject.model.entity.UserDetails;
import HungerNet.FinalProject.model.entity.enums.RoleEnum;
import HungerNet.FinalProject.service.RestaurantService;
import HungerNet.FinalProject.service.RoleService;
import HungerNet.FinalProject.service.UserDetailsService;
import HungerNet.FinalProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.List;

public class UserDTO {

    @Autowired
    private UserService userService;


    private UserDetailsService userDetailsService;

    private PasswordEncoder encoder;
    private RoleService roleService;

    private RestaurantService restaurantService;
    private String firstName;

    private String lastName;

    private List<RoleEnum> role;

    private List<Integer> roles;

    private List<Integer> restaurants;
    private String email;

    private String username;

    private String phoneNumber;

    private Date dateCreated;

    private String createdBy;
    private String password;
    private String modifiedBy;
    private Boolean isNonLocked;

    private Boolean enabled;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getNonLocked() {
        return isNonLocked;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNonLocked(Boolean nonLocked) {
        isNonLocked = nonLocked;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public List<Integer> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(List<Integer> restaurants) {
        this.restaurants = restaurants;
    }

    public List<RoleEnum> getRole() {
        return role;
    }

    public void setRole(List<RoleEnum> role) {
        this.role = role;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getCreatedBy() {
        return createdBy;
    }


    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Integer> getRoles() {
        return roles;
    }

    public void setRoles(List<Integer> roles) {
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public User convertDtoToUser(UserDTO userDTO) {

        User loggedInUser = userService.findByUsername(userDTO.getCreatedBy()).get();

        User user = new User();


        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setCreatedBy(loggedInUser);
        user.setAccountNonLocked(userDTO.getNonLocked());
        return user;
    }

    public UserDetails convertDtoToUserDetails(UserDTO userDTO) {

        UserDetails user = new UserDetails();

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber());

        return user;
    }

}


