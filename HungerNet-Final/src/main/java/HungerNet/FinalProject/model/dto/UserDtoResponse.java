package HungerNet.FinalProject.model.dto;

import HungerNet.FinalProject.model.entity.Role;
import HungerNet.FinalProject.model.entity.User;
import HungerNet.FinalProject.model.entity.enums.RoleEnum;
import HungerNet.FinalProject.service.RoleService;
import HungerNet.FinalProject.service.UserDetailsService;
import HungerNet.FinalProject.service.UserService;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserDtoResponse {

    private UserService userService;
    private UserDetailsService userDetailsService;
    private RoleService roleService;

    private Integer id;
    private String firstName;

    private String lastName;

    private List<RoleEnum> role;

    private String email;

    private String username;

    private String phoneNumber;

    private Date dateCreated;

    private String createdBy;

    private String modifiedBy;

    private Boolean isNonLocked;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public List<RoleEnum> getRole() {
        return role;
    }

    public void setRole(List<RoleEnum> role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getDateCreated() {
        return dateCreated;
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

    public Boolean getNonLocked() {
        return isNonLocked;
    }

    public void setNonLocked(Boolean nonLocked) {
        isNonLocked = nonLocked;
    }

    public UserDtoResponse convertUserToDto(User user) {
        Set<Role> role1 = user.getRoles();

        UserDtoResponse userDTO = new UserDtoResponse();
        userDTO.setId(user.getId());
        userDTO.setPhoneNumber(user.getUserDetails().getPhoneNumber());
        userDTO.setRole(role1.stream().map(Role::getRole).collect(Collectors.toList()));
        userDTO.setUsername(user.getUsername());
        userDTO.setFirstName(user.getUserDetails().getFirstName());
        userDTO.setLastName(user.getUserDetails().getLastName());
        userDTO.setEmail(user.getUserDetails().getEmail());
        userDTO.setDateCreated(user.getUserDetails().getCreatedDate());
        userDTO.setCreatedBy(user.getCreatedBy().getUsername());
        userDTO.setModifiedBy(user.getModifiedBy().getUsername());
        userDTO.setNonLocked(user.getAccountNonLocked());

        return userDTO;
    }
}
