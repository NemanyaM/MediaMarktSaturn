package services;

import com.mediamarktsaturn.MediaMarktSaturn.domain.models.Product;
import com.mediamarktsaturn.MediaMarktSaturn.domain.repositories.ProductRepository;
import com.mediamarktsaturn.MediaMarktSaturn.domain.services.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductService service;

    @Test
    public void shouldReturnAllProducts() {
        List<Product> products = new ArrayList();
        products.add(new Product());

        given(repository.findAll()).willReturn(products);

        List<Product> expected = service.getAllProducts();

        assertEquals(expected, products);
        verify(repository).findAll();
    }

    @Test
    public void whenGivenId_shouldReturnProduct_ifFound() {
        Product product = new Product();
        product.setId(89L);
        product.setName("prod name");
        product.setOnlineStatus("BLOCKED");
        product.setShortDescription("short des");
        product.setLongDescription("long");

        when(repository.findById(product.getId())).thenReturn(Optional.of(product));

        Product expected = service.getProductById(product.getId());

        assertThat(expected).isSameAs(product);
        verify(repository).findById(product.getId());
    }

    @Test(expected = NoResultException.class)
    public void should_throw_exception_when_product_detail_doesnt_exist() {
        Product product = new Product();
        product.setId(89L);
        product.setName("prod name");
        product.setOnlineStatus("BLOCKED");
        product.setShortDescription("short des");
        product.setLongDescription("long");

        given(repository.findById(anyLong())).willReturn(Optional.ofNullable(null));
        service.getProductById(product.getId());
    }

    @Test
    public void whenSaveProduct_shouldReturnProduct() {
        Product product = new Product();
        product.setId(89L);
        product.setName("prod name");
        product.setOnlineStatus("BLOCKED");
        product.setShortDescription("short des");
        product.setLongDescription("long");

        when(repository.save(ArgumentMatchers.any(Product.class))).thenReturn(product);

        Product created = service.createProduct(product);

        assertThat(created.getName()).isSameAs(product.getName());
        verify(repository).save(product);
    }

    @Test
    public void whenGivenId_shouldUpdateProduct_ifFound() {
        Product product = new Product();
        product.setId(89L);
        product.setName("prod name");
        product.setOnlineStatus("BLOCKED");
        product.setShortDescription("short des");
        product.setLongDescription("long");

        Product newProduct = new Product();
        newProduct.setId(89L);
        newProduct.setName("prod name 1");
        newProduct.setOnlineStatus("BLOCKED");
        newProduct.setShortDescription("short desc");
        newProduct.setLongDescription("long desc");

        given(repository.findById(product.getId())).willReturn(Optional.of(product));
        service.updateProduct(product.getId(), newProduct);

        verify(repository).save(newProduct);
        verify(repository).findById(product.getId());
    }

    @Test(expected = NoResultException.class)
    public void should_throw_exception_when_product_doesnt_exist() {
        Product product = new Product();
        product.setId(89L);
        product.setName("prod name");
        product.setOnlineStatus("BLOCKED");
        product.setShortDescription("short des");
        product.setLongDescription("long");

        Product newProduct = new Product();
        newProduct.setId(89L);
        newProduct.setName("prod name 1");
        newProduct.setOnlineStatus("BLOCKED");
        newProduct.setShortDescription("short desc");
        newProduct.setLongDescription("long desc");

        given(repository.findById(anyLong())).willReturn(Optional.ofNullable(null));
        service.updateProduct(product.getId(), newProduct);
    }

    @Test
    public void whenGivenId_shouldDeleteProduct_ifFound(){
        Product product = new Product();
        product.setId(89L);
        product.setName("prod name");
        product.setOnlineStatus("BLOCKED");
        product.setShortDescription("short des");
        product.setLongDescription("long");

        when(repository.findById(product.getId())).thenReturn(Optional.of(product));

        service.deleteProduct(product.getId());
        verify(repository).findById(product.getId());
    }

    @Test(expected = NoResultException.class)
    public void should_throw_exception_when_product_deleted_doesnt_exist() {
        Product product = new Product();
        product.setId(89L);
        product.setName("prod name");
        product.setOnlineStatus("BLOCKED");
        product.setShortDescription("short des");
        product.setLongDescription("long");

        given(repository.findById(anyLong())).willReturn(Optional.ofNullable(null));
        service.deleteProduct(product.getId());
    }
}
