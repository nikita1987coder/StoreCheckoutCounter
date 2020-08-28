package com.storecheckoutcounter.service;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.storecheckoutcounter.dataaccesslayer.entity.Item;
import com.storecheckoutcounter.dataaccesslayer.entity.Product;
import com.storecheckoutcounter.dataaccesslayer.repository.ItemRepository;
import com.storecheckoutcounter.dataaccesslayer.repository.ProductRepository;
import com.storecheckoutcounter.rest.controller.CustomException;
import com.storecheckoutcounter.rest.controller.product.beans.ProductInfo;

@Service
public class ProductService {

	final Logger logger = LogManager.getLogger(getClass());

	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private ItemRepository lineItemRepo;

	public Product createProduct(ProductInfo productInfo) {
		logger.info("Input recieved to create product = " + productInfo);
		verifyIfProductExists(productInfo.getBarCodeId());
		Product product = new Product();
		product.setBarCodeId(productInfo.getBarCodeId());
		product.setName(productInfo.getName());
		product.setProductCategory(productInfo.getProductCategory());
		product.setRate(productInfo.getRate());

		product = productRepo.save(product);
		logger.info("Created product with id = " + product.getId());
		return product;

	}

	public void deleteProduct(Long id) {
		verifyProductExists(id);
		verifyLineItemExists(id);
		productRepo.delete(id);
	}

	private void verifyLineItemExists(Long id) {
		logger.info("Verifying if the a line item exists with an id = " + id);
		List<Item> lineItems = lineItemRepo.findByProduct_id(id);
		if (null!= lineItems && !lineItems.isEmpty()) {
			logger.info("line items associated with product " + lineItems);
			throw new CustomException("Product -  " + id + " is associated with bills. Cannot be deleted.");
		}
	}
		
		


	public Iterable<Product> getAllProducts() {
		Iterable<Product> products = productRepo.findAll();
		logger.info("returning all products");
		return products;
	}

	public Product getProductById(Long id) {
		verifyProductExists(id);
		return productRepo.findOne(id);
	}

	public Product updateProduct(ProductInfo productInfo, Long id) {
		verifyProductExists(id);
		Product product = productRepo.findOne(id);
		product.setBarCodeId(productInfo.getBarCodeId());
		product.setName(productInfo.getName());
		product.setProductCategory(productInfo.getProductCategory());
		product.setRate(productInfo.getRate());
		Product p = productRepo.save(product);
		logger.info("updated product id = " + product.getId());
		return p;
	}

	private void verifyIfProductExists(String barCodeId) {
		List<Product> productsByBarCodeID = productRepo.findByBarCodeId(barCodeId);
		if (null != productsByBarCodeID && !productsByBarCodeID.isEmpty()) {
			logger.info("Problem with input data: BarCode ID  " + barCodeId + " already exists in Product Master");
			throw new CustomException(
					"Problem with input data: BarCode ID  " + barCodeId + " already exists in Product Master");
		}
	}

	private void verifyProductExists(Long id) {
		logger.info("Verifying if the product exists with an id = " + id);
		Product product = productRepo.findOne(id);
		if (product == null) {
			throw new CustomException("Product with id " + id + " not found");
		}
	}

}
