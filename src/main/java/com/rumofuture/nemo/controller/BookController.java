package com.rumofuture.nemo.controller;

import com.rumofuture.nemo.model.domain.Book;
import com.rumofuture.nemo.model.dto.Response;
import com.rumofuture.nemo.service.BookService;
import com.rumofuture.nemo.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 漫画册业务API
 *
 * @author 王振琦
 * createAt: 2018/08/01
 * updateAt: 2018/11/06
 */
@RestController
@RequestMapping(value = "api")
public class BookController extends NemoFacade {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping(value = "books")
    public Response<Book> actionSaveBook(@RequestBody Book book) {
        book.setAuthor(getCurrentUser());
        bookService.saveBook(book);
        return new Response<>(book);
    }

    @PutMapping(value = "books")
    public Response<Book> actionUpdateBook(@RequestBody Book book) {
        bookService.updateBook(book);
        return new Response<>();
    }

    @PutMapping(value = "books/{id}/publish")
    public Response<Book> actionPublishBook(@PathVariable(value = "id") Integer id) {
        bookService.publishBook(id);
        return new Response<>();
    }

    @DeleteMapping(value = "books/{id}")
    public Response<Book> actionDeleteBook(@PathVariable(value = "id") Integer id) {
        bookService.deleteBook(id);
        return new Response<>();
    }

    @GetMapping(value = "books/{id}")
    public Response<Book> actionQueryBookContainsAuthor(@PathVariable(value = "id") Integer id) {
        Book book = bookService.findBookContainsAuthorAndPageList(id);
        return new Response<>(book);
    }

    @GetMapping(value = "books")
    public Response<List<Book>> actionQueryBookList() {
        List<Book> bookList = bookService.findBookList();
        return new Response<>(bookList);
    }

    @GetMapping(value = "books/index")
    public Response<List<Book>> actionQueryBookListForIndexPage() {
        List<Book> bookList = bookService.findBookListForIndex();
        return new Response<>(bookList);
    }
}
