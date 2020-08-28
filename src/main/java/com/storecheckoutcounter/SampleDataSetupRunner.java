package com.storecheckoutcounter;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.storecheckoutcounter.dataaccesslayer.entity.Bill;
import com.storecheckoutcounter.rest.controller.bill.beans.BillUpdateInfo;
import com.storecheckoutcounter.rest.controller.bill.beans.ProductInfoForBill;
import com.storecheckoutcounter.rest.controller.product.beans.ProductInfo;
import com.storecheckoutcounter.service.BillService;
import com.storecheckoutcounter.service.ProductService;
import com.storecheckoutcounter.util.BillStatus;
import com.storecheckoutcounter.util.ProductCategory;

@Component
public class SampleDataSetupRunner implements CommandLineRunner {

	@Autowired
	private BillService billService;

	final Logger logger = LogManager.getLogger(getClass());

	@Autowired
	private ProductService productService;

	@Override
	public void run(String... arg0) throws Exception {
		logger.info("Inside Runner..");
		setUpProductData();
		setupBillData();
		logger.info("Exiting Runner.. ");
	}

	public void setupBillData() {

		// create a new Bill to update information.
		Bill o1 = billService.createBill(new Bill(0.0, 0, BillStatus.IN_PROGRESS));

		Long billId = o1.getId();
		BillUpdateInfo billUpdateInfo = new BillUpdateInfo();
		List<ProductInfoForBill> productsToBeAdded = new ArrayList<>();
		List<ProductInfoForBill> productsToBeRemoved = new ArrayList<>();

		productsToBeAdded.add(new ProductInfoForBill("ABC-abc-0001", 2));
		productsToBeAdded.add(new ProductInfoForBill("ABC-abc-0002", 2));
		productsToBeAdded.add(new ProductInfoForBill("ABC-abc-0003", 2));
		productsToBeAdded.add(new ProductInfoForBill("ABC-abc-0004", 2));
		productsToBeAdded.add(new ProductInfoForBill("ABC-abc-0005", 2));
		billUpdateInfo.setProductsToBeAdded(productsToBeAdded);
		billUpdateInfo.setProductsToBeRemoved(productsToBeRemoved);
		billUpdateInfo.setStatus(BillStatus.RELEASED);

		logger.info("billUpdateInfo = " + billUpdateInfo);
		billService.updateBill(billUpdateInfo, billId);
		Bill retrieveUpdatedbill = billService.getBillById(o1.getId());
		logger.info("retrieveUpdatedbill = " + retrieveUpdatedbill.getNoOfItems() + "  value ="
				+ retrieveUpdatedbill.getTotalValue());

	}

	private void setUpProductData() {
		productService.createProduct(new ProductInfo("ABC-abc-0001", 20.0, "TV", ProductCategory.A));
		productService.createProduct(new ProductInfo("ABC-abc-0002", 30.0, "Headphone", ProductCategory.B));
		productService.createProduct(new ProductInfo("ABC-abc-0003", 40.0, "Tab", ProductCategory.C));
		productService.createProduct(new ProductInfo("ABC-abc-0004", 50.0, "Laptop", ProductCategory.A));
		productService.createProduct(new ProductInfo("ABC-abc-0005", 60.0, "iPad", ProductCategory.B));
		productService.createProduct(new ProductInfo("ABC-abc-0006", 70.0, "Macbook", ProductCategory.C));
		productService.createProduct(new ProductInfo("ABC-abc-0007", 80.0, "Speaker", ProductCategory.A));
		productService.createProduct(new ProductInfo("ABC-abc-0008", 90.0, "keyboard", ProductCategory.B));
		productService.createProduct(new ProductInfo("ABC-abc-0009", 100.0, "Adapter", ProductCategory.C));
		productService.createProduct(new ProductInfo("ABC-abc-0010", 110.0, "Hard disk", ProductCategory.A));
	}
}
