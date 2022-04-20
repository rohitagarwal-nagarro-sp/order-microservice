package com.nagp.ordermicroservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nagp.ordermicroservice.entity.OrderMaster;
import com.nagp.ordermicroservice.repo.OrderRepository;

@Service
public class OrderService {
 
	@Autowired
	OrderRepository orderRepository;

	public List<OrderMaster> findAll() {
		return orderRepository.findAll();
	}
	
	public List<OrderMaster> findByStatus(String status) {
		return orderRepository.findByStatus(status);
	}
	
	public Optional<OrderMaster> findById(int orderId) {
		return orderRepository.findById(orderId);
	}
	
	public OrderMaster save(OrderMaster employee) {
		return orderRepository.save(employee);
	}
	
	public List<OrderMaster> trackService() {
		return orderRepository.findAll();
	}
	
	
}
