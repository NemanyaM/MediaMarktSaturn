package services;

import com.mediamarktsaturn.MediaMarktSaturn.domain.models.Category;
import com.mediamarktsaturn.MediaMarktSaturn.domain.repositories.CategoryRepository;
import com.mediamarktsaturn.MediaMarktSaturn.domain.services.CategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.assertj.core.api.Assertions.assertThat;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository repository;

    @InjectMocks
    private CategoryService service;

    @Test
    public void shouldReturnAllCategories() {
        List<Category> categories = new ArrayList();
        categories.add(new Category());

        given(repository.findAll()).willReturn(categories);

        List<Category> expected = service.getAllCategories();

        assertEquals(expected, categories);
        verify(repository).findAll();
    }

    @Test
    public void whenGivenId_shouldReturnCategory_ifFound() {
        Category category = new Category();
        category.setId(89L);
        category.setName("cat name");
        category.setParentId(202);

        when(repository.findById(category.getId())).thenReturn(Optional.of(category));

        Category expected = service.getCategoryById(category.getId());

        assertThat(expected).isSameAs(category);
        verify(repository).findById(category.getId());
    }

    @Test(expected = NoResultException.class)
    public void should_throw_exception_when_category_detail_doesnt_exist() {
        Category category = new Category();
        category.setId(89L);
        category.setName("cat name");
        category.setParentId(222);

        given(repository.findById(anyLong())).willReturn(Optional.ofNullable(null));
        service.getCategoryById(category.getId());
    }

    @Test
    public void whenSaveCategory_shouldReturnCategory() {
        Category category = new Category();
        category.setId(89L);
        category.setName("cat name");
        category.setParentId(222);

        when(repository.save(ArgumentMatchers.any(Category.class))).thenReturn(category);

        Category created = service.createCategory(category);

        assertThat(created.getName()).isSameAs(category.getName());
        verify(repository).save(category);
    }

    @Test
    public void whenGivenId_shouldUpdateCategory_ifFound() {
        Category category = new Category();
        category.setId(89L);
        category.setName("cat name");
        category.setParentId(222);

        Category newCategory = new Category();
        newCategory.setId(89L);
        newCategory.setName("cat name new");
        newCategory.setParentId(223);

        given(repository.findById(category.getId())).willReturn(Optional.of(category));
        service.updateCategory(category.getId(), newCategory);

        verify(repository).save(newCategory);
        verify(repository).findById(category.getId());
    }

    @Test(expected = NoResultException.class)
    public void should_throw_exception_when_category_doesnt_exist() {
        Category category = new Category();
        category.setId(89L);
        category.setName("cat name");
        category.setParentId(222);

        Category newCategory = new Category();
        newCategory.setId(89L);
        newCategory.setName("cat name new");
        newCategory.setParentId(223);

        given(repository.findById(anyLong())).willReturn(Optional.ofNullable(null));
        service.updateCategory(category.getId(), newCategory);
    }

    @Test
    public void whenGivenId_shouldDeleteCategory_ifFound(){
        Category category = new Category();
        category.setId(89L);
        category.setName("cat name");
        category.setParentId(222);

        when(repository.findById(category.getId())).thenReturn(Optional.of(category));

        service.deleteCategory(category.getId());
        verify(repository).findById(category.getId());
    }

    @Test(expected = NoResultException.class)
    public void should_throw_exception_when_category_deleted_doesnt_exist() {
        Category category = new Category();
        category.setId(89L);
        category.setName("cat name");
        category.setParentId(222);

        given(repository.findById(anyLong())).willReturn(Optional.ofNullable(null));
        service.deleteCategory(category.getId());
    }
}
