package fel.cvut.order.rest.controllers;

import fel.cvut.order.exception.NotFoundException;
import fel.cvut.order.model.Category;
import fel.cvut.order.rest.requests.CategoryResponse;
import fel.cvut.order.rest.util.RestUtils;
import fel.cvut.order.service.CategoryService;
import fel.cvut.order.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final OrderService orderService;

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createCategory(HttpServletRequest request, @RequestBody Category category) {
        int id = RestUtils.getCookieUserId(request.getCookies());
        if (id == 0)
            throw new NotFoundException("You aren't logged in");
        category.setCreatorId(id);
        categoryService.createCategory(category);
        log.info("Created category {}.", category);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", category.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @GetMapping
    public List<CategoryResponse> getAllCategories() {
        return categoryService.findAllCategories().stream()
            .map(category -> new CategoryResponse(
                category.getId(),
                category.getName()))
            .collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CategoryResponse getById(@PathVariable Integer id) {
        Category category = categoryService.findById(id);

        if (category == null)
            throw NotFoundException.create("Category", id);

        return new CategoryResponse(
            category.getId(),
            category.getName()
        );
    }

    @GetMapping(value = "/{id}/categories", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CategoryResponse> getCategoriesByOrder(@PathVariable Integer id) {
        List<Category> categories = categoryService.findCategoriesByOrder(orderService.findById(id));

        return categories.stream()
            .map(category -> new CategoryResponse(
                category.getId(),
                category.getName()))
            .collect(Collectors.toList());
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable Integer id) {
        final Category category = categoryService.findById(id);

        if (category == null)
            throw NotFoundException.create("Category", id);

        categoryService.deleteCategory(category);
        log.debug("Category {} was deleted.", category);
    }
}
