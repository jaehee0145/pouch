package my.examples.pouch.controller.api;

import lombok.RequiredArgsConstructor;
import my.examples.pouch.domain.Account;
import my.examples.pouch.domain.Category;
import my.examples.pouch.dto.CustomCategory;
import my.examples.pouch.dto.CustomLink;
import my.examples.pouch.service.AccountService;
import my.examples.pouch.service.CategoryService;
import my.examples.pouch.service.LinkService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoriesController {
    private final CategoryService categoryService;
    private final LinkService linkService;
    private final AccountService accountService;
    //검색해서 카테고리 목록 가져오기
    @GetMapping(value = "/search")
    public List<CustomCategory> searchCategories(@RequestParam(name = "searchType") int searchType,
                         @RequestParam(name = "searchStr") String searchStr) {
        List<Category> categories = categoryService.getCategoriesBySearch(searchType, searchStr);

        System.out.println("cate"+categories.size());
        List<CustomCategory> list = categoryService.getCustomCategory(categories);

        return list;
    }

    //특정 유저의 카테고리 목록 가져오기
    @GetMapping
    public List<CustomCategory> getCategoryByAccount(@RequestParam(name = "email") String email) {
        List<Category> categories = categoryService.findMyCategoryList(email);
        List<CustomCategory> list = categoryService.getCustomCategory(categories);
        return list;
    }

    //특정 카테고리 가져오기
    @GetMapping(value = "/{id}")
    public CustomCategory getCategory(@PathVariable(value="id") Long id){
        Category category = categoryService.getCategory(id);
        CustomCategory customCategory = new CustomCategory();
        customCategory.setId(category.getId());
        customCategory.setName(category.getCategoryName());
        customCategory.setLinks(linkService.getCustomLinks(category.getLinks()));
        return customCategory;
    }

    @PostMapping
    public void addCategory(@RequestParam(name = "name") String name,
                            Principal principal){
        Account account = accountService.findAccountByEmail(principal.getName());
        Category category = new Category();
        category.setCategoryName(name);
        category.setAccount(account);
        categoryService.addCategory(category);
    }

    @PutMapping(value = "/{id}")
    public void editCategory(@PathVariable(value="id") Long id){

    }

    //카테고리 삭제
    @DeleteMapping(value = "/{id}")
    public void deleteCategory(@PathVariable(value = "id") Long id){

    }

    // 다른 유저의 카테고리를 복사해서 내 카테고리로 저장하기
    /*@PostMapping
    public void shareCategory(@RequestParam(name = "id") Long id){

    }*/
}

