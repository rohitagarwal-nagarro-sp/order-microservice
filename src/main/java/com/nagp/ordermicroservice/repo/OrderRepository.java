package com.nagp.ordermicroservice.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nagp.ordermicroservice.entity.OrderMaster;

@Repository
public interface OrderRepository extends JpaRepository<OrderMaster, Integer>
{
	public List<OrderMaster> findByStatus(String status);
}
