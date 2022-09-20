package HungerNet.FinalProject.service.impl;

import HungerNet.FinalProject.controllers.UserController;
import HungerNet.FinalProject.model.entity.Item;

import HungerNet.FinalProject.repository.ItemRepository;

import HungerNet.FinalProject.service.ItemService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemRepository itemRepository;

    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Override
    public Optional<Item> findById(Integer id) {
        try {
            return itemRepository.findById(id);
        } catch (Exception exception) {
            logger.error(exception.getMessage());
        }
        return null;
    }


    @Override
    public List<Item> findAllItems() {
        try {
            return itemRepository.findAll();
        } catch (Exception exception) {
            logger.error(exception.getMessage());
        }
        return null;
    }

    @Override
    public void saveItem(Item item) {
        try {
            itemRepository.save(item);
        } catch (Exception exception) {
            logger.error(exception.getMessage());
        }
    }

    @Override
    public void deleteItem(Item item) {
        try {
            itemRepository.delete(item);
        } catch (Exception exception) {
            logger.error(exception.getMessage());
        }

    }
}
