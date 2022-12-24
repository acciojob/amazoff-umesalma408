package com.driver;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class OrderRepository {
    HashMap<String, Order> orderInDB = new HashMap<>();
    HashMap<DeliveryPartner, LinkedList<String>> deliveryPartnersInDB = new HashMap<>();

    public void addOrder(Order order) {
        orderInDB.put(order.getId(), order);
    }

    public void addPartner(String partnerId) {
        // List<String> orderListOfPartners = new LinkedList<String>();
        DeliveryPartner deliveryPartner = new DeliveryPartner(partnerId);
        LinkedList<String> list = new LinkedList<>();
        deliveryPartnersInDB.put(deliveryPartner, list);
        //ListOfDeliveryPartnersInDB.put(partnerId, orderListOfPartners);
    }

    public void assignAllOrderPartnerPairToDB(String orderId, String partnerId) {
        for (DeliveryPartner deliveryPartner : deliveryPartnersInDB.keySet()) {
            if (Objects.equals(partnerId, deliveryPartner.getId())) {
                deliveryPartner.setNumberOfOrders(deliveryPartner.getNumberOfOrders() + 1);
                LinkedList<String>
                        list = deliveryPartnersInDB.get(deliveryPartner);
                list.add(orderId);
                deliveryPartnersInDB.put(deliveryPartner, list);
                return;
            }
        }
    }

    public Order getOrderPartnerPairFromDB(String orderId) {
        return orderInDB.get(orderId);
    }

    public DeliveryPartner getAllPartnerByIdFromDB(String partnerId) {
        for (DeliveryPartner deliveryPartner : deliveryPartnersInDB.keySet()) {
            if (Objects.equals(partnerId, deliveryPartner.getId())) {
                return deliveryPartner;
            }
        }
        return null;
    }

    public Integer getAllOrderCountByPartnerId(String partnerId) {
        for (DeliveryPartner deliveryPartner : deliveryPartnersInDB.keySet()) {
            if (Objects.equals(partnerId, deliveryPartner.getId())) {
                return deliveryPartner.getNumberOfOrders();

            }
        }
        return 0;
    }

    public List<String> getOrdersByDeliveryPartnersFromDB(String partnerId) {
        for (DeliveryPartner deliveryPartner : deliveryPartnersInDB.keySet()) {
            if (Objects.equals(partnerId, deliveryPartner.getId())) {
                return deliveryPartnersInDB.get(deliveryPartner);

            }
        }
        return null;
    }

    public List<String> findAllOrdersFromDB() {
        List<String> list = new LinkedList<>();
        list.addAll(orderInDB.keySet());
        return list;
    }

    public void deletePartnersByIdFromDB(String partnerId) {
        List<String> list = new LinkedList<>();
        for (DeliveryPartner deliveryPartner : deliveryPartnersInDB.keySet()) {
            if (Objects.equals(deliveryPartner.getId(), partnerId)) {
                list = deliveryPartnersInDB.get(deliveryPartner);
                deliveryPartnersInDB.remove(deliveryPartner);
                break;
            }
        }
                for (String order : list) {
                    orderInDB.remove(order);

                }
            }

    public void deleteAllOrderIdFromDB(String orderId) {
        orderInDB.remove(orderId);
        for (DeliveryPartner deliveryPartner : deliveryPartnersInDB.keySet()) {
            LinkedList<String> orderList = deliveryPartnersInDB.get(deliveryPartner);
            if (orderList.remove(orderId)) {
                deliveryPartner.setNumberOfOrders(deliveryPartner.getNumberOfOrders() - 1);
                deliveryPartnersInDB.put(deliveryPartner, orderList);
                return;
            }
        }
    }
        //getAllCountOfUnassignedOrder
        //public Integer getCountOfUnassignedOrders() {
        // return orderList.size()-assignList.size();
        public Integer getCountOfUnassignedOrders() {
            int count = 0;
            HashMap<String, Integer> map = new HashMap<>();
            for (List<String> list : deliveryPartnersInDB.values()) {
                for (String order : list) {
                    map.put(order, 1);
                }
            }
            for (String str : orderInDB.keySet()) {
                if (!map.containsKey(str))
                    count++;
            }
            return count;

        }

        public Integer getAllOrderLeftAfterTimePartnerId (String time, String partnerId){
            //public Integer getOrderLeftAfterTimePartnerId(String time,String id) {
            int count = 0;
            int currTime = Integer.parseInt(time.substring(0, 2)) * 60 + Integer.parseInt(time.substring(3, 5));
            List<String> list = new LinkedList<>();
            for (DeliveryPartner dp : deliveryPartnersInDB.keySet()) {
                if (Objects.equals(partnerId, dp.getId())) {
                    list = deliveryPartnersInDB.get(dp);
                    break;
                }
            }
            for (String orderName : list) {
                Order order = orderInDB.get(orderName);
                if (currTime > order.getDeliveryTime()) {
                    count++;
                }
            }
            return count;
            //return count;

        }

        public String getLastDeliveryTimeByPartnerId (String partnerId){
            String hh = "", mm = "", zero = "0";
            for (DeliveryPartner dp : deliveryPartnersInDB.keySet()) {
                if (Objects.equals(partnerId, dp.getId())) {
                    String str = deliveryPartnersInDB.get(dp).getLast();
                    int temp = orderInDB.get(str).getDeliveryTime();
                    hh = String.valueOf((temp / 60 < 10) ? (zero + temp / 60) : temp / 60);
                    mm = String.valueOf((temp % 60 < 10) ? (zero + temp % 60) : temp % 60);

                }
            }
            return hh + ":" + mm;
        }

    }
