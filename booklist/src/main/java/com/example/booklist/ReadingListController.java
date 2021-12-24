package com.example.booklist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller  // 使用了 controller 注解， 这样组件扫描会自动将其注册为spring程序上下文中的一个bean;
@RequestMapping("/readingList") // 这个注解 将所有的方法都映射到了 readingList 上;
public class ReadingListController {
    private ReadingListRepository readingListRepository;

    @Autowired
    public ReadingListController(
            ReadingListRepository readingListRepository
    ){
        this.readingListRepository = readingListRepository;
    }

    @RequestMapping(value="/{reader}", method = RequestMethod.GET)  // 处理 /{reader} 上的 http 请求 ;
    public String readersBooks(
            @PathVariable("reader") String reader, Model model) {


        List<Book> readingList = readingListRepository.findByReader(reader);

        if(readingList != null){
            model.addAttribute("books", readingList);
        }
        return "readingList";

    }

    @RequestMapping(value="/{reader}", method = RequestMethod.POST)
    public String addToReadingList(
            @PathVariable("reader") String reader, Book book) {
        book.setReader(reader);
        readingListRepository.save(book);
        return "redirect:/readingList/{reader}";
    }
}
