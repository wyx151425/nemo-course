package com.rumofuture.nemo.service.impl;

import com.rumofuture.nemo.model.domain.Book;
import com.rumofuture.nemo.model.domain.Favorite;
import com.rumofuture.nemo.model.domain.User;
import com.rumofuture.nemo.repository.BookRepository;
import com.rumofuture.nemo.repository.FavoriteRepository;
import com.rumofuture.nemo.repository.UserRepository;
import com.rumofuture.nemo.service.FavoriteService;
import com.rumofuture.nemo.util.Constant;
import com.rumofuture.nemo.util.Generator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 收藏业务逻辑实现
 *
 * @author 王振琦
 * createAt: 2018/11/07
 * updateAt: 2019/01/08
 */
@Service(value = "favoriteService")
public class FavoriteServiceImpl implements FavoriteService {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final FavoriteRepository favoriteRepository;

    @Autowired
    public FavoriteServiceImpl(
            UserRepository userRepository, BookRepository bookRepository, FavoriteRepository favoriteRepository
    ) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.favoriteRepository = favoriteRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveFavorite(Favorite favorite) {
        favorite.setObjectId(Generator.getObjectId());
        favorite.setStatus(Constant.Status.GENERAL);
        favoriteRepository.save(favorite);
        Book book = bookRepository.findBookById(favorite.getBook().getId());
        int star = book.getFavor();
        book.setFavor(++star);
        bookRepository.update(book);
        User user = userRepository.findUserById(favorite.getUser().getId());
        int favor = user.getFavorite();
        user.setFavorite(++favor);
        userRepository.update(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteFavorite(Integer id) {
        Favorite favorite = favoriteRepository.findFavoriteById(id);
        favoriteRepository.delete(id);
        Book book = bookRepository.findBookById(favorite.getBookId());
        int star = book.getFavor();
        book.setFavor(--star);
        bookRepository.update(book);
        User user = userRepository.findUserById(favorite.getUserId());
        int favor = user.getFavorite();
        user.setFavorite(--favor);
        userRepository.update(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Favorite findFavoriteByBookAndUser(Integer bookId, Integer userId) {
        return favoriteRepository.findFavoriteByBookIdAndUserId(bookId, userId);
    }

    @Override
    public List<Book> findFavoriteBookList(Integer userId) {
        return favoriteRepository.findFavoriteBookList(userId);
    }
}
