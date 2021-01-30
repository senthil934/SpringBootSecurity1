package com.pack.SpringBootSecurity.test;





import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;


import com.pack.SpringBootSecurity.model.Product;
import com.pack.SpringBootSecurity.repository.ProductRepsitory;
import com.pack.SpringBootSecurity.service.ProductService;



@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootServiceTest {
	
	@Autowired
	private ProductService productService;
	
	@MockBean
	private ProductRepsitory productRepo;
	
	@Test
	//@Ignore
	public void testCreateProduct(){
		Product product = new Product(12,"Mobile","Redmi","Japan",20000.0);
	    Mockito.when(productRepo.save(product)).thenReturn(product);    
	    assertThat(productService.saveProduct(product)).isEqualTo(product);
	}
	
	@Test
	public void testGetAllProducts(){
		List<Product> productList=null;
		Mockito.when(productRepo.findAll()).thenReturn(productList);		
		assertThat(productService.fetchAllProduct()).isEqualTo(productList);
	}
	
	@Test
	public void testDeleteProduct(){
		Product product = new Product(); 
		Mockito.when(productRepo.findById(11)).thenReturn(Optional.of(product));
	    Mockito.when(productRepo.existsById(product.getId())).thenReturn(false);
	    assertFalse(productRepo.existsById(product.getId()));
	}
	
	
	@Test
	public void testUpdateTicket(){
		Product product = new Product(20,"fan","orient","India",2500.0);
		Mockito.when(productRepo.findById(20)).thenReturn(Optional.of(product));
	
		product.setBrand("usha");
		Mockito.when(productRepo.save(product)).thenReturn(product);
		assertThat(productService.updateProduct(product)).isEqualTo(product);
		
	}
	

}
