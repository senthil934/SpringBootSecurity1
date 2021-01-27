package com.pack.SpringBootSecurity.controller;


import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import com.pack.SpringBootSecurity.model.Product;
import com.pack.SpringBootSecurity.service.ProductService;
import com.pack.SpringBootSecurity.service.UserService;
import com.pack.SpringBootSecurity.validator.UserValidator;
import org.mockito.internal.matchers.InstanceOf;



@RunWith(SpringRunner.class)
@WebMvcTest(value=UserController.class,secure = false)
public class SpringBootControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProductService productService;
	
	@MockBean
	private UserService userService;
	
	@MockBean
	private UserValidator userValidator;
	
	@Test
	public void testaddProduct() throws Exception {
        mockMvc.perform(post("/save")
                .param("id", "1")
                .param("name", "Heater")
                .param("brand","samsung")
                .param("madein", "India")
                .param("price", "20000.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/welcome"));
                
        ArgumentCaptor<Product> argCap=ArgumentCaptor.forClass(Product.class);
        Mockito.verify(productService).saveProduct(argCap.capture());
        assertEquals("Heater",argCap.getValue().getName());
	}
	
	@Test
    public void testEditProduct(){
        Product product=new Product(30,"AC","LG","Japan",40000.0);
        Mockito.when(productService.getProductById(Mockito.anyInt())).thenReturn(product);
        ArgumentCaptor<Product> argCap=ArgumentCaptor.forClass(Product.class);
        product.setBrand("Onida");
        productService.updateProduct(product);
        Mockito.verify(productService).updateProduct(argCap.capture());
        assertEquals("Onida",argCap.getValue().getBrand());
    }
	
	@Test
    public void testDeleteProductById() throws Exception {
	    Integer id = 1;
        mockMvc.perform(get("/deleteproduct/1"))
                .andExpect(status().is3xxRedirection()) //status code in 300 range
                .andExpect(view().name("redirect:/welcome"));
        verify(productService, times(1)).deleteProduct(id);
    }

	@Test
    public void testAccessDenied() throws Exception {
        mockMvc.perform(get("/403"))
        .andExpect(status().isOk())
        .andExpect(view().name("403"));
    }

	@Test
	public void testCreateProduct() throws Exception {
		mockMvc.perform(get("/new"))
        .andExpect(status().isOk())
        .andExpect(view().name("new_product"))
        .andExpect(model().size(1))
        .andExpect(model().attributeExists("productForm"));
	}
}
