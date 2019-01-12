package com.rumofuture.nemo.service;

import com.rumofuture.nemo.model.domain.Book;

import java.util.List;

/**
 * 漫画册业务逻辑接口
 *
 * @author 王振琦
 * createAt: 2018/08/01
 * updateAt: 2018/11/06
 */
public interface BookService {
    /**
     * 保存漫画册
     *
     * @param book 漫画册数据对象
     */
    void saveBook(Book book);

    /**
     * 更新漫画册
     * @param book 漫画册数据对象
     */
    void updateBook(Book book);

    /**
     * 删除课程
     * @param id 课程数据对象
     */
    void deleteBook(Integer id);

    /**
     * 获取漫画册对象集合
     *
     * @return 漫画册对象集合
     */
    List<Book> findBookList();

    /**
     * 根据用户ID获取用户创建的漫画册集合
     *
     * @param authorId 用户ID
     * @return 漫画册集合
     */
    List<Book> findBookListByAuthor(Integer authorId);

    /**
     * 根据漫画册ID获取漫画册及其包含的漫画分页
     *
     * @param id 漫画册ID
     * @return 包含漫画分页的漫画册
     */
    Book findBookContainsAuthorAndPageList(Integer id);

    /**
     * 获取首页的漫画册数据集合
     *
     * @return 漫画册数据集合
     */
    List<Book> findBookListForIndex();
}
