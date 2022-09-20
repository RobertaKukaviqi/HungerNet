package HungerNet.FinalProject.service;

import HungerNet.FinalProject.model.entity.Item;
import HungerNet.FinalProject.model.entity.Menu;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ItemService {

    Optional<Item> findById(Integer id);

    List<Item> findAllItems();

    void saveItem(Item item);

    void deleteItem(Item item);
}
