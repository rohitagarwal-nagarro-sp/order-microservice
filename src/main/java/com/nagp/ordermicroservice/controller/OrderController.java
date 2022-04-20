package com.nagp.ordermicroservice.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nagp.ordermicroservice.entity.OrderMaster;
import com.nagp.ordermicroservice.entity.OrderStatusEnum;
import com.nagp.ordermicroservice.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController 
{
	@Autowired
	OrderService orderService;
	
	@GetMapping("/getAllServiceRequests")
	public ResponseEntity<List<OrderMaster>> getAllUser() 
	{
		List<OrderMaster> list=orderService.findAll();
		return new ResponseEntity<List<OrderMaster>>(list, new HttpHeaders(), HttpStatus.OK);
	}
	
	@GetMapping("/trackServiceRequestById")
	public ResponseEntity<OrderMaster> getServiceRequestById(@RequestParam("orderId") int orderId) 
	{
		OrderMaster orderObj = getOrderRec(orderId);
		if (orderObj != null) {
            return new ResponseEntity<>(orderObj,new HttpHeaders(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/getServiceRequestByStatus")
	public ResponseEntity<List<OrderMaster>> getServiceRequestByStatus(@RequestParam("status") String status) 
	{
		List<OrderMaster> list=orderService.findByStatus(status);
		return new ResponseEntity<List<OrderMaster>>(list, new HttpHeaders(), HttpStatus.OK);
	}
	
	@PostMapping("/bookServiceRequest")
	public ResponseEntity<OrderMaster> bookService(@RequestBody OrderMaster order)
	{
		order.setStatus(OrderStatusEnum.CONFIRMED);
		order.setServiceProviderId(0);
		OrderMaster service=orderService.save(order);
		if(service == null) {
			return new ResponseEntity<OrderMaster>(service,new HttpHeaders(),HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<OrderMaster>(service,new HttpHeaders(), HttpStatus.OK);
	}

	@PutMapping("/assignServiceRequestToProvider")
	public ResponseEntity<OrderMaster> assignServiceRequestToProvider(@RequestParam("orderId") int orderId, @RequestParam("serviceProviderId") int serviceProviderId)
	{
		OrderMaster order = orderService.findById(orderId).orElse(null);
		if(order == null) {
			return new ResponseEntity<OrderMaster>(order, new HttpHeaders(),HttpStatus.BAD_REQUEST);
		}
		else 
		{
			order.setStatus(OrderStatusEnum.PROCESSING);
			order.setServiceProviderId(serviceProviderId);
			OrderMaster service=orderService.save(order);
			return new ResponseEntity<OrderMaster>(service,new HttpHeaders(), HttpStatus.OK);
		}
	}
	
	@PutMapping("/cancelServiceRequest")
	public ResponseEntity<OrderMaster> cancelServiceRequest(@RequestParam("orderId") int orderId)
	{
		OrderMaster order = orderService.findById(orderId).orElse(null);
		if(order == null) {
			return new ResponseEntity<OrderMaster>(order, new HttpHeaders(),HttpStatus.BAD_REQUEST);
		}
		else 
		{
			order.setStatus(OrderStatusEnum.CANCELLED);
			order.setServiceProviderId(0);
			OrderMaster service=orderService.save(order);
			return new ResponseEntity<OrderMaster>(service,new HttpHeaders(), HttpStatus.OK);
		}
	}
	
	@PutMapping("/completeServiceRequest")
	public ResponseEntity<OrderMaster> completeServiceRequest(@RequestParam("orderId") int orderId)
	{
		OrderMaster order = orderService.findById(orderId).orElse(null);
		if(order == null) {
			return new ResponseEntity<OrderMaster>(order, new HttpHeaders(),HttpStatus.BAD_REQUEST);
		}
		else 
		{
			order.setStatus(OrderStatusEnum.COMPLETED);
			OrderMaster service=orderService.save(order);
			return new ResponseEntity<OrderMaster>(service,new HttpHeaders(), HttpStatus.OK);
		}
	}
	
	private OrderMaster getOrderRec(int id) 
	{
        Optional<OrderMaster> orderObj = orderService.findById(id);
        if (orderObj.isPresent()) 
        {
            return orderObj.get();
        }
        return null;
    }
}