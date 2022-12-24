package com.driver;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class OrderService {
    @Autowired
    OrderRepository orderRepository;
    public void addOrder(Order order) {
        orderRepository.addOrder(order);
    }

    public void addPartner(String partnerId) {
        orderRepository.addPartner(partnerId);
    }

    public void assignOrderPartnerPairToDB(String orderId, String partnerId) {
        orderRepository.assignAllOrderPartnerPairToDB(orderId,partnerId);
    }

    public Order getOrderById(String orderId) {
        return orderRepository.getOrderPartnerPairFromDB(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        return orderRepository.getAllPartnerByIdFromDB(partnerId);
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
        return orderRepository.getAllOrderCountByPartnerId(partnerId);
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        return orderRepository.getOrdersByDeliveryPartnersFromDB(partnerId);
    }

    public List<String> findAllOrders() {
        return orderRepository.findAllOrdersFromDB();
    }

    public void deleteAllPartnersById(String partnerId) {
        orderRepository.deletePartnersByIdFromDB(partnerId);
    }

    public void deleteAllOrderId(String orderId) {

        orderRepository.deleteAllOrderIdFromDB(orderId);
    }

    public Integer getCountOfUnassignedOrder() {

        return orderRepository.getCountOfUnassignedOrders();
    }

    public Integer getOrderLeftAfterTimePartnerId(String time, String partnerId) {
        return orderRepository.getAllOrderLeftAfterTimePartnerId(time,partnerId);
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId) {
        return orderRepository.getLastDeliveryTimeByPartnerId(partnerId);

    }
}

