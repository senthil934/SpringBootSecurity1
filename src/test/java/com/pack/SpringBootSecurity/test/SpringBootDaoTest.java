package com.pack.SpringBootSecurity.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.pack.SpringBootSecurity.model.Product;
import com.pack.SpringBootSecurity.repository.ProductRepsitory;



@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootDaoTest {
	
	@Autowired
	private ProductRepsitory productRepo;
	
	@Test
	public void testSaveProduct(){
        Product product = new Product(10,"TV","LG","China",20000.0);
        Product savedInDb = productRepo.save(product);
        Optional<Product> data = productRepo.findById(savedInDb.getId());
        Product getFromDb = (Product)data.get();
        //assertThat(getFromDb).isEqualTo(savedInDb);
        assertEquals(savedInDb.getId(),getFromDb.getId());
	}
	
	@Test
	public void testGetAllProducts(){
		Iterable<Product> allProductsFromDb = productRepo.findAll();
		List<Product> productList = new ArrayList<>();
		
		for (Product product : allProductsFromDb) {
			productList.add(product);
		}
		assertThat(productList.size()).isEqualTo(4);
	}

	@Test
	public void testDeleteProductById(){
		productRepo.deleteById(10);
		Iterable<Product> allProductsFromDb = productRepo.findAll();
		List<Product> productList = new ArrayList<>();
		
		for (Product product : allProductsFromDb) {
			productList.add(product);
		}
		assertThat(productList.size()).isEqualTo(4);
	}
	
	@Test
	public void testUpdateProduct(){
		Product product = new Product(11,"Fridge","LG","China",30000.0);
        Product savedInDb = productRepo.save(product);
        Optional<Product> data = productRepo.findById(savedInDb.getId());
        Product getFromDb = (Product)data.get();
		getFromDb.setPrice(35000.0);
		productRepo.save(getFromDb);
		
		assertThat(getFromDb.getPrice()).isEqualTo(35000.0);
	}
}
