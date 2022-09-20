package HungerNet.FinalProject.service;

import HungerNet.FinalProject.model.dto.MenuDto;
import HungerNet.FinalProject.model.entity.Menu;
import HungerNet.FinalProject.model.entity.Restaurant;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface MenuService {
    Optional<Menu> findById(Integer id);

    List<Menu> findAllMenus();

    List<Menu> findMenuByRestaurantId(Integer id);

    void saveMenu(Menu menu);

    void deleteMenu(Menu menu);
}
