package com.ecommerce.shipping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.shipping.bean.ShippingResponseBean;
import com.ecommerce.shipping.entity.Shipment;
import com.ecommerce.shipping.proxy.OrderServiceProxy;
import com.ecommerce.shipping.repository.ShippingOrderRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ShippingService {

    @Autowired
    private ShippingOrderRepository repository;
    
    @Autowired
    private OrderServiceProxy orderService;
    
    public Shipment createShippingOrder(Shipment shipment) {
        return repository.save(shipment);
    }
    
    
    public ShippingResponseBean getById(Long orderId) {
    	Shipment shipment= repository.findById(orderId).orElseThrow(()->  new RuntimeException("Shipping not found"));
    	var orderDetails = orderService.getOrderById(shipment.getOrderId()).getBody(); 
    	var result = new ShippingResponseBean(shipment, orderDetails);
        return result;
    }
    
	public Shipment updateshipping(Long orderId, Shipment shipment) {
	        Optional<Shipment> existingProduct = repository.findByOrderId(orderId);
	        if (existingProduct.isPresent()) {
	        	Shipment shipupdate = existingProduct.get();
	        	shipupdate.setDestination(shipment.getDestination());
	        	shipupdate.setShippingCost(shipment.getShippingCost());
	        	shipupdate.setShippingDate(shipment.getShippingDate());
	        	shipupdate.setOrderId(shipment.getOrderId());
	        	shipupdate.setUserId(shipment.getUserId());
	        	shipupdate.setStatus(shipment.getStatus());
	            return repository.save(shipment);
	        } else {
	            throw new RuntimeException("Shipping not found");
	        }
	    
	}

	public void deleteShipping(Long id) {
		repository.deleteById(id);
    }


	public List<Shipment>  findAll() {
		return repository.findAll();
	}

}