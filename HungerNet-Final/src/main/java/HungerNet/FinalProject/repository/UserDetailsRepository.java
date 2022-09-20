package HungerNet.FinalProject.repository;

import HungerNet.FinalProject.model.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailsRepository extends JpaRepository<UserDetails,Integer> {
}
