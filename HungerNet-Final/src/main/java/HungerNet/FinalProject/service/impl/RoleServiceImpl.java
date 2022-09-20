package HungerNet.FinalProject.service.impl;

import HungerNet.FinalProject.model.entity.Role;
import HungerNet.FinalProject.repository.RoleRepository;
import HungerNet.FinalProject.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;


    @Override
    public Optional<Role> findById(Integer id) {
        return roleRepository.findById(id);
    }
}
