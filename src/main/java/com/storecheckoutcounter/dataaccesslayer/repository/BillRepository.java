package com.storecheckoutcounter.dataaccesslayer.repository;

import org.springframework.data.repository.CrudRepository;
import com.storecheckoutcounter.dataaccesslayer.entity.Bill;

public interface BillRepository extends CrudRepository<Bill, Long> {

}
