package com.storecheckoutcounter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.storecheckoutcounter.dataaccesslayer.entity.Product;
import com.storecheckoutcounter.dataaccesslayer.repository.ProductRepository;
import com.storecheckoutcounter.util.ProductCategory;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StoreCheckoutCounterApplicationTests {

	@Autowired
	private ProductRepository productMasterRepo;

	@Test
	public void contextLoads() {
	}

	public void testProductSetup() {
		productMasterRepo.save(new Product("ABC-abc-1234", 20.0, "TV", ProductCategory.A));
		productMasterRepo.save(new Product("ABC-abc-1235", 40.0, "Headphone", ProductCategory.B));
		productMasterRepo.save(new Product("ABC-abc-1236", 50.0, "Tab", ProductCategory.C));
		productMasterRepo.save(new Product("ABC-abc-1237", 35.0, "Laptop", ProductCategory.A));
		productMasterRepo.save(new Product("ABC-abc-1238", 100.0, "iPad", ProductCategory.B));
		productMasterRepo.save(new Product("ABC-abc-1239", 49.0, "Macbook", ProductCategory.C));
		assertThat(productMasterRepo.count()).isEqualTo(6);
	}

}
