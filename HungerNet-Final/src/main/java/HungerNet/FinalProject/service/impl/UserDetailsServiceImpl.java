package HungerNet.FinalProject.service.impl;


import HungerNet.FinalProject.model.entity.UserDetails;
import HungerNet.FinalProject.repository.UserDetailsRepository;

import HungerNet.FinalProject.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Override
    public void saveUser(UserDetails user) {
        userDetailsRepository.save(user);
    }
}
