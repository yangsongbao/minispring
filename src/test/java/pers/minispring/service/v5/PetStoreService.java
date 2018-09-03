package pers.minispring.service.v5;


import pers.minispring.beans.factory.annotation.Autowired;
import pers.minispring.dao.v5.AccountDao;
import pers.minispring.dao.v5.ItemDao;
import pers.minispring.stereotype.Component;
import pers.minispring.util.MessageTracker;

@Component(value = "petStore")
public class PetStoreService {
    @Autowired
    AccountDao accountDao;
    @Autowired
    ItemDao itemDao;

    public PetStoreService() {

    }

    public ItemDao getItemDao() {
        return itemDao;
    }

    public AccountDao getAccountDao() {
        return accountDao;
    }

    public void placeOrder() {
        System.out.println("place order");
        MessageTracker.addMsg("place order");
    }
}
