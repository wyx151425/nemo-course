package cn.edu.qfnu.rumo.controller;

import cn.edu.qfnu.rumo.model.domain.Book;
import cn.edu.qfnu.rumo.model.domain.Favorite;
import cn.edu.qfnu.rumo.model.dto.Response;
import cn.edu.qfnu.rumo.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 收藏业务API
 *
 * @author 王振琦
 * createAt: 2018/11/06
 * updateAt: 2019/01/08
 */
@RestController
@RequestMapping(value = "api")
public class FavoriteController {

    private final FavoriteService favoriteService;

    @Autowired
    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping(value = "favorites")
    public Response<Favorite> actionSaveFavorite(@RequestBody Favorite favorite) {
        favoriteService.saveFavorite(favorite);
        return new Response<>(favorite);
    }

    @DeleteMapping(value = "favorites/{id}")
    public Response<Favorite> actionDeleteFavorite(@PathVariable(value = "id") Integer id) {
        favoriteService.deleteFavorite(id);
        return new Response<>();
    }

    @GetMapping(value = "favorites")
    public Response<Favorite> actionQueryFavoriteByBookAndUser(
            @RequestParam(value = "bookId") Integer bookId, @RequestParam(value = "userId") Integer userId
    ) {
        Favorite favorite = favoriteService.findFavoriteByBookAndUser(bookId, userId);
        return new Response<>(favorite);
    }

    @GetMapping(value = "favorites/book")
    public Response<List<Book>> actionQueryFavoriteBookList(
            @RequestParam(value = "userId") Integer userId
    ) {
        List<Book> bookList = favoriteService.findFavoriteBookList(userId);
        return new Response<>(bookList);
    }
}
