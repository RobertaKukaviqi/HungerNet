package HungerNet.FinalProject.service;

import HungerNet.FinalProject.model.entity.Role;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface RoleService {

    Optional<Role> findById(Integer id);

}
