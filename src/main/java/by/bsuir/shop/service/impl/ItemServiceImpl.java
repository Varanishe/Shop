package by.bsuir.shop.service.impl;

import by.bsuir.shop.dao.ItemDAO;
import by.bsuir.shop.dao.exception.DAOException;
import by.bsuir.shop.dao.factory.DAOFactory;
import by.bsuir.shop.entity.ShopItem;
import by.bsuir.shop.service.ItemService;
import by.bsuir.shop.service.exception.ServiceException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ItemServiceImpl implements ItemService{
    public final static String ATTR_NAME = "item_name";
    public final static String ATTR_DESCRIPTION = "description";
    public final static String ATTR_PRICE = "price";
    public final static String ATTR_CATEGORY = "category";
    public final static String ATTR_IMAGE = "image";
    public final static String ATTR_ITEM_ID = "item_id";

    private final static Logger logger = LogManager.getLogger(ItemServiceImpl.class.getName());

    @Override
    public List<ShopItem> getAllItems(String category) throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        ItemDAO dao = factory.getItemDAO();
        List<ShopItem> items = new ArrayList<>();

        try {
            items = dao.getAllItems(category);
        } catch (DAOException ex){
            logger.error(ex);
            throw new ServiceException(ex);
        }

        return items;
    }

    @Override
    public boolean addNewItem(Map<String, String> params) throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        ItemDAO dao = factory.getItemDAO();

        for (String param : params.values()){
            if(!checkForNull(param)){
                return false;
            }
        }

        if(!validatePrice(params.get(ATTR_PRICE))) {
            return false;
        }


        boolean status;
        ShopItem shopItem = new ShopItem();
        try {
            Double price = Double.parseDouble(params.get(ATTR_PRICE));

            shopItem.setPrice(price);
            shopItem.setImageUrl(params.get(ATTR_IMAGE));
            shopItem.setCategory(params.get(ATTR_CATEGORY));
            shopItem.setName(params.get(ATTR_NAME));
            shopItem.setDescription(params.get(ATTR_DESCRIPTION));

            status = dao.addNewItem(shopItem);
        } catch (NumberFormatException ex){
            logger.error(ex);
            throw new ServiceException(ex);
        } catch (DAOException ex){
            logger.error(ex);
            throw new ServiceException(ex);
        }

        return status;
    }

    @Override
    public ShopItem loadItem(String id) throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        ItemDAO dao = factory.getItemDAO();
        ShopItem item = null;

        try {
            Integer itemId = Integer.parseInt(id);
            item = dao.getItemById(itemId);
        } catch (NumberFormatException ex){
            logger.error(ex);
            throw new ServiceException(ex);
        } catch (DAOException ex){
            logger.error(ex);
            throw new ServiceException(ex);
        }

        return item;
    }

    @Override
    public boolean editItem(Map<String, String> params) throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        ItemDAO dao = factory.getItemDAO();
        boolean status = false;

        for (String param : params.values()){
            if(!checkForNull(param)){
                return false;
            }
        }

        if(!validatePrice(params.get(ATTR_PRICE))){
            return false;
        }

        ShopItem shopItem = new ShopItem();
        try {
            Integer itemId = Integer.parseInt(params.get(ATTR_ITEM_ID));
            Double price = Double.parseDouble(params.get(ATTR_PRICE));

            shopItem.setId(itemId);
            shopItem.setPrice(price);
            shopItem.setImageUrl(params.get(ATTR_IMAGE));
            shopItem.setCategory(params.get(ATTR_CATEGORY));
            shopItem.setName(params.get(ATTR_NAME));
            shopItem.setDescription(params.get(ATTR_DESCRIPTION));

            status = dao.editItem(shopItem);
        } catch (NumberFormatException ex){
            logger.error(ex);
            throw new ServiceException(ex);
        } catch (DAOException ex){
            logger.error(ex);
            throw new ServiceException(ex);
        }

        return status;
    }

    @Override
    public boolean deleteItem(String id) throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        ItemDAO dao = factory.getItemDAO();
        boolean status = false;

        if(!checkForNull(id)){
            return false;
        }

        Integer itemId;
        try {
            itemId = Integer.parseInt(id);
            status = dao.deleteItem(itemId);
        } catch (NumberFormatException ex){
            logger.error(ex);
            return false;
        } catch (DAOException ex){
            logger.error(ex);
            throw new ServiceException(ex);
        }

        return status;
    }

    @Override
    public List<ShopItem> searchItems(String querry) throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        ItemDAO dao = factory.getItemDAO();
        List<ShopItem> items = new ArrayList<>();

        if (!checkForNull(querry)){
            return null;
        }

        try {
            items = dao.searchItems(querry);
        } catch (DAOException ex){
            logger.error(ex);
            throw new ServiceException(ex);
        }

        return items;
    }

    private boolean checkForNull(String ... args){
        for (String arg : args){
            if ((arg == null)||(arg.isEmpty())){
                return false;
            }
        }

        return true;
    }

    private boolean validatePrice(String price){
        String decimalRegExp = "\\d*(\\.\\d{1,2})?";

        return price.matches(decimalRegExp);
    }
}