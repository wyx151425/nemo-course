package com.rumofuture.nemo.service.impl;

import com.rumofuture.nemo.model.domain.Book;
import com.rumofuture.nemo.model.domain.User;
import com.rumofuture.nemo.repository.BookRepository;
import com.rumofuture.nemo.repository.UserRepository;
import com.rumofuture.nemo.service.BookService;
import com.rumofuture.nemo.util.Constant;
import com.rumofuture.nemo.util.Generator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 漫画册业务逻辑类
 *
 * @author 王振琦
 * createAt: 2018/08/01
 * updateAt: 2018/11/09
 */
@Service(value = "bookService")
public class BookServiceImpl implements BookService {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(UserRepository userRepository, BookRepository bookRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveBook(Book book) {
        book.setObjectId(Generator.getObjectId());
        book.setStatus(Constant.BookStatus.CREATED);
        book.setFavor(0);
        book.setPage(0);
        bookRepository.save(book);

        User user = userRepository.findUserById(book.getAuthor().getId());
        int bookQuantity = user.getBook();
        user.setBook(++bookQuantity);
        userRepository.update(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<Book> findBookList() {
        return bookRepository.findBookList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<Book> findBookListByAuthor(Integer authorId) {
        return bookRepository.findBookListByAuthorId(authorId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Book findBookContainsAuthorAndPageList(Integer id) {
        Book book = bookRepository.findBookById(id);
        book.getAuthor();
        book.getPageList();
        return book;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<Book> findBookListForIndex() {
        return bookRepository.findBookListWithLimit(5);
    }
}
