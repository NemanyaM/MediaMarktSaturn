package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediamarktsaturn.MediaMarktSaturn.MediaMarktSaturnApplication;
import com.mediamarktsaturn.MediaMarktSaturn.domain.models.Category;
import com.mediamarktsaturn.MediaMarktSaturn.domain.services.CategoryService;
import com.mediamarktsaturn.MediaMarktSaturn.presentation.controllers.CategoryController;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.runner.RunWith;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import utils.JsonUtil;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.mockito.Mockito;

import javax.persistence.NoResultException;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.when;



@RunWith(SpringRunner.class)
@WebMvcTest(CategoryController.class)
@ContextConfiguration(classes={MediaMarktSaturnApplication.class})
public class CategoryControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private CategoryService service;

    @Test
    public void getAllCategories() throws Exception {
        List<Category> list = new ArrayList<>();
        list.add(new Category(1L, "test device", null));
        list.add(new Category(2L, "Drink a beer", 202));
        when(service.getAllCategories()).thenReturn(list);

        mockMvc.perform(MockMvcRequestBuilders.get("/categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2))).andDo(print());
    }

    @Test
    public void listCategoryById_whenGetMethod() throws Exception {
        Category category = new Category();
        category.setName("Test Name");
        category.setId(89L);
        category.setParentId(666);

        given(service.getCategoryById(category.getId())).willReturn(category);

        mockMvc.perform(get("/categories/" + category.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void should_throw_exception_when_category_doesnt_exist() throws Exception {
        Category category = new Category();
        category.setName("Test Name");
        category.setId(89L);
        category.setParentId(661);

        Mockito.doThrow(new NoResultException("category.getId()")).when(service).getCategoryById(category.getId());

        mockMvc.perform(get("/categories/" + category.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createCategory_whenPostMethod() throws Exception {
        Category category = new Category();
        category.setName("test name");
        given(service.createCategory(category)).willReturn(category);
        mockMvc.perform(post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJson(category)))
                .andExpect(status().isCreated());
    }

    @Test
    public void removeCategoryById_whenDeleteMethod() throws Exception {
        Category category = new Category();
        category.setName("Test Name");
        category.setId(89L);
        category.setParentId(12);

        mockMvc.perform(delete("/categories/" + category.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void should_throw_exception_when_category_to_remove_doesnt_exist() throws Exception {
        Category category = new Category();
        category.setId(89L);
        category.setName("Test Name");
        category.setParentId(13);

        Mockito.doThrow(new NoResultException("category.getId()")).when(service).deleteCategory(category.getId());

        mockMvc.perform(delete("/categories/" + category.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

    }

    @Test
    public void updateCategory_whenPutCategory() throws Exception {
        Category category = new Category();
        category.setName("Test Name");
        category.setId(89L);
        category.setParentId(899);
        given(service.updateCategory(category.getId(), category)).willReturn(category);

        ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(put("/categories/" + category.getId().toString())
                        .content(mapper.writeValueAsString(category))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void should_throw_exception_when_category_to_update_doesnt_exist() throws Exception {
        Category category = new Category();
        category.setId(89L);
        category.setName("Test Name");
        category.setParentId(666);

        Mockito.doThrow(new NoResultException("category.getId()")).when(service).updateCategory(category.getId(), category);
        ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(put("/categories/" + category.getId().toString())
                        .content(mapper.writeValueAsString(category))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
