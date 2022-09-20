package HungerNet.FinalProject.repository;

import HungerNet.FinalProject.model.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item,Integer> {
}
