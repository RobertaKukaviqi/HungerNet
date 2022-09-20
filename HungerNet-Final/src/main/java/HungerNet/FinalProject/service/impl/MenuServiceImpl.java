package HungerNet.FinalProject.service.impl;

import HungerNet.FinalProject.controllers.UserController;
import HungerNet.FinalProject.model.dto.MenuDto;
import HungerNet.FinalProject.model.entity.Menu;
import HungerNet.FinalProject.repository.MenuRepository;
import HungerNet.FinalProject.repository.RestaurantRepository;
import HungerNet.FinalProject.repository.UserRepository;
import HungerNet.FinalProject.service.MenuService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuRepository menuRepository;

    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Override
    public Optional<Menu> findById(Integer id) {
        return menuRepository.findById(id);
    }

    @Override
    public List<Menu> findAllMenus() {
        try {

            Date today =new Date();
            Integer hour=today.getHours();
            return menuRepository.findAllActiveMenus(hour);

        }
        catch (Exception exception){
            logger.error(exception.getMessage());
        }
        return null;
    }

    @Override
    public List<Menu> findMenuByRestaurantId(Integer id) {
        Date today =new Date();
        Integer hour=today.getHours();
        return menuRepository.findMenusByRestaurantId(id, hour);
    }

    @Override
    public void saveMenu(Menu menu) {
     menuRepository.save(menu);
    }

    @Override
    public void deleteMenu(Menu menu) {

    }
}
