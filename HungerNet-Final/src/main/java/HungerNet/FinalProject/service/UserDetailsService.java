package HungerNet.FinalProject.service;

import HungerNet.FinalProject.model.entity.UserDetails;
import org.springframework.stereotype.Service;

@Service
public interface UserDetailsService {

    void saveUser(UserDetails user);

}
