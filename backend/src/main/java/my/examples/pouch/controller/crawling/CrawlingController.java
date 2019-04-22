package my.examples.pouch.controller.crawling;

import lombok.RequiredArgsConstructor;
import my.examples.pouch.domain.Account;
import my.examples.pouch.domain.Category;
import my.examples.pouch.domain.Link;
import my.examples.pouch.repository.LinkRepository;
import my.examples.pouch.service.CategoryService;
import my.examples.pouch.service.AccountService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/crawling")
@RequiredArgsConstructor
public class CrawlingController {
    private final LinkRepository linkRepository;
    private final AccountService accountService;
    private final CategoryService categoryService;

    @PostMapping("/save")
    public void crawling(@RequestParam(required = true) String url,
                           @RequestParam(required = true) Long categoryId,
                           Principal principal) throws Exception{
        Document doc = Jsoup.connect(url).timeout(5*1000).get();
        Elements title = doc.select("title");
        Elements sub = doc.select("meta[name=description]");
        String content = title.html();
        if(content == null){
            content = sub.attr("content");
        }
        Category category = categoryService.getCategory(categoryId);
        Account account = accountService.findAccountByEmail(principal.getName());
        Link link = new Link();
        link.setTitle(content);
        link.setUrl(url);
        link.setEmail(principal.getName());
        link.setLinkOption(0L);
        link.setRepository(account.getId());
        link.setCategory(category);
        link.setAccount(account);
        linkRepository.save(link);
        //return "redirect:/link/view/"+categoryId;
    }
}
