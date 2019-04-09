package Project;

import java.util.ArrayList;

public class Supplier extends Account implements java.io.Serializable {

    private ArrayList<Order> deliveryOrderList;

    public Supplier(String username, String plainText) {
        super(username, plainText);
        this.deliveryOrderList = new ArrayList<>();
    }

    public Supplier(String username, String plainText, ArrayList<Order> deliveryOrderList) {
        super(username, plainText);
        this.deliveryOrderList = deliveryOrderList;
    }

    public void addToDeliveryOrderList(Order order) {
        deliveryOrderList.add(order);
    }

    public void removeFromDeliveryOrderList(Order order) {
        deliveryOrderList.remove(order);
    }

    public ArrayList<Order> getDeliveryOrderList() {
        return deliveryOrderList;
    }

    /*public void processDeliveryOrder(Order order, Storefront storefront){

        for(Shipment shipment : order.getOrderedItems()){
            if(storefront.inventoryCheck(shipment.getItem(), shipment.getAmount())){

            } else{
                throw new OutOfStockException();
            }


        }
    }*/
}
