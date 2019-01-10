package com.rumofuture.nemo.service.impl;

import com.rumofuture.nemo.model.domain.Book;
import com.rumofuture.nemo.model.domain.Page;
import com.rumofuture.nemo.repository.BookRepository;
import com.rumofuture.nemo.repository.PageRepository;
import com.rumofuture.nemo.service.PageService;
import com.rumofuture.nemo.util.Constant;
import com.rumofuture.nemo.util.Generator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 漫画分页业务逻辑类
 *
 * @author 王振琦
 * createAt: 2018/08/01
 * updateAt: 2018/10/10
 */
@Service(value = "pageService")
public class PageServiceImpl implements PageService {

    private final BookRepository bookRepository;
    private final PageRepository pageRepository;

    @Autowired
    public PageServiceImpl(BookRepository bookRepository, PageRepository pageRepository) {
        this.bookRepository = bookRepository;
        this.pageRepository = pageRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(Page page) {
        Book book = bookRepository.findBookById(page.getBook().getId());
        int pageTotal = book.getPage();
        pageTotal++;
        book.setPage(pageTotal);
        bookRepository.update(book);
        page.setObjectId(Generator.getObjectId());
        page.setStatus(Constant.Status.GENERAL);
        pageRepository.save(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id) {
        Page page = pageRepository.findPageById(id);
        pageRepository.delete(id);
        Book book = page.getBook();
        int pageQuantity = book.getPage();
        pageQuantity--;
        book.setPage(pageQuantity);
        bookRepository.update(book);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Page page) {
        pageRepository.update(page);
    }

    @Override
    public Page findPageContainsBook(Integer id) {
        Page page = pageRepository.findPageById(id);
        page.getBook();
        return page;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<Page> findPageListByBook(Integer bookId) {
        return pageRepository.findPageListByBookId(bookId);
    }
}
