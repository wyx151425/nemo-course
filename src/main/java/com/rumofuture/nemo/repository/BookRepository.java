package com.rumofuture.nemo.repository;

import com.rumofuture.nemo.model.domain.Book;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 漫画册类DAO
 *
 * @author 王振琦
 * createAt: 2018/08/01
 * updateAt: 2018/11/09
 */
@Repository(value = "bookRepository")
public interface BookRepository {
    /**
     * 保存漫画册对象
     *
     * @param book 漫画册对象
     */
    void save(Book book);

    /**
     * 更新漫画册对象
     *
     * @param book 漫画册对象
     */
    void update(Book book);

    /**
     * 删除课程对象
     *
     * @param id 课程对象ID
     */
    void delete(Integer id);

    /**
     * 根据ID查询漫画册对象
     *
     * @param id 漫画册ID
     * @return 漫画册对象
     */
    Book findBookById(Integer id);

    /**
     * 获取漫画册对象集合
     *
     * @return 漫画册对象集合
     */
    List<Book> findBookList();

    /**
     * 获取漫画册对象集合
     *
     * @param limit 限制
     * @return 漫画册对象集合
     */
    List<Book> findBookListWithLimit(int limit);

    /**
     * 根据漫画册的作者ID获取该作者的漫画册集合
     *
     * @param authorId 漫画册作者ID
     * @return 漫画册对象集合
     */
    List<Book> findBookListByAuthorId(Integer authorId);

    /**
     * 根据漫画册的作者ID获取该作者的漫画册集合
     *
     * @param authorId 漫画册作者ID
     * @return 漫画册对象集合
     */
    List<Book> findPublishedBookListByAuthorId(Integer authorId);
}
