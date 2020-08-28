package com.storecheckoutcounter.dataaccesslayer.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.storecheckoutcounter.dataaccesslayer.entity.Item;

public interface ItemRepository extends CrudRepository<Item, Long> {
	
	public List<Item> findByProduct_id(long prodId);

}
