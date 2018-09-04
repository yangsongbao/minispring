package pers.minispring.service.v6;


import pers.minispring.stereotype.Component;
import pers.minispring.util.MessageTracker;

@Component(value="petStore")
public class PetStoreService implements IPetStoreService {
	
	public PetStoreService() {		
		
	}
	
	public void placeOrder(){
		System.out.println("place order");
		MessageTracker.addMsg("place order");
	}

}
