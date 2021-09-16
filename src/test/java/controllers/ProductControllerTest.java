package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediamarktsaturn.MediaMarktSaturn.MediaMarktSaturnApplication;
import com.mediamarktsaturn.MediaMarktSaturn.domain.models.Product;
import com.mediamarktsaturn.MediaMarktSaturn.domain.services.ProductService;
import com.mediamarktsaturn.MediaMarktSaturn.presentation.controllers.ProductController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import utils.JsonUtil;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
@ContextConfiguration(classes={MediaMarktSaturnApplication.class})
public class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ProductService service;

    @Test
    public void getAllProducts() throws Exception {
        List<Product> list = new ArrayList<>();
        list.add(new Product(1L, "test device", "ACTIVE", "long Desc", "short desc"));
        list.add(new Product(2L, "test device2", "BLOCKED", "long Desc", "short desc"));
        when(service.getAllProducts()).thenReturn(list);

        mockMvc.perform(MockMvcRequestBuilders.get("/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2))).andDo(print());
    }

    @Test
    public void listProductById_whenGetMethod() throws Exception {
        Product product = new Product();
        product.setName("Test Name");
        product.setId(89L);
        product.setOnlineStatus("ACTIVE");
        product.setLongDescription("long desc");
        product.setShortDescription("short desc");

        given(service.getProductById(product.getId())).willReturn(product);

        mockMvc.perform(get("/products/" + product.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void should_throw_exception_when_product_doesnt_exist() throws Exception {
        Product product = new Product();
        product.setName("Test Name");
        product.setId(89L);
        product.setOnlineStatus("ACTIVE");
        product.setLongDescription("long desc");
        product.setShortDescription("short desc");

        Mockito.doThrow(new NoResultException("products.getId()")).when(service).getProductById(product.getId());

        mockMvc.perform(get("/products/" + product.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createProduct_whenPostMethod() throws Exception {
        Product product = new Product();
        product.setName("Test Name");
        product.setId(89L);
        product.setOnlineStatus("ACTIVE");
        product.setLongDescription("long desc");
        product.setShortDescription("short desc");

        given(service.createProduct(product)).willReturn(product);
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(product)))
                .andExpect(status().isCreated());
    }

    @Test
    public void removeProductById_whenDeleteMethod() throws Exception {
        Product product = new Product();
        product.setName("Test Name");
        product.setId(89L);
        product.setOnlineStatus("ACTIVE");
        product.setLongDescription("long desc");
        product.setShortDescription("short desc");

        mockMvc.perform(delete("/products/" + product.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void should_throw_exception_when_product_to_remove_doesnt_exist() throws Exception {
        Product product = new Product();
        product.setName("Test Name");
        product.setId(89L);
        product.setOnlineStatus("ACTIVE");
        product.setLongDescription("long desc");
        product.setShortDescription("short desc");

        Mockito.doThrow(new NoResultException("product.getId()")).when(service).deleteProduct(product.getId());

        mockMvc.perform(delete("/products/" + product.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

    }

    @Test
    public void updateProduct_whenPutCategory() throws Exception {
        Product product = new Product();
        product.setName("Test Name");
        product.setId(89L);
        product.setOnlineStatus("ACTIVE");
        product.setLongDescription("long desc");
        product.setShortDescription("short desc");

        given(service.updateProduct(product.getId(), product)).willReturn(product);

        ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(put("/products/" + product.getId().toString())
                        .content(mapper.writeValueAsString(product))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void should_throw_exception_when_product_to_update_doesnt_exist() throws Exception {
        Product product = new Product();
        product.setName("Test Name");
        product.setId(89L);
        product.setOnlineStatus("ACTIVE");
        product.setLongDescription("long desc");
        product.setShortDescription("short desc");

        Mockito.doThrow(new NoResultException("product.getId()")).when(service).updateProduct(product.getId(), product);
        ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(put("/products/" + product.getId().toString())
                        .content(mapper.writeValueAsString(product))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
