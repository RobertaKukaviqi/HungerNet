package HungerNet.FinalProject.repository;

import HungerNet.FinalProject.model.entity.Menu;
import HungerNet.FinalProject.model.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu,Integer> {

    @Transactional
    @Query("Select restaurant.menus from Restaurant restaurant where restaurant.id=?1")
    List<Menu> findMenusByRestaurantId(Integer id);

    @Transactional
    @Query(nativeQuery = true, value = "Select * from restaurant inner join menu on restaurant.id=menu.restaurant_id where restaurant.id=?1" +
        " and ?2 BETWEEN menu.start_date and menu.end_date")
    List<Menu> findMenusByRestaurantId(Integer id, Integer hour);

    @Transactional
    @Query(nativeQuery = true,value="select * from menu WHERE ?1 BETWEEN menu.start_date and menu.end_date")
    List<Menu> findAllActiveMenus(Integer day);

}
