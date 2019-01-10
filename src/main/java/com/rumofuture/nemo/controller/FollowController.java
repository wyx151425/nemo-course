package com.rumofuture.nemo.controller;

import com.rumofuture.nemo.model.domain.Follow;
import com.rumofuture.nemo.model.domain.User;
import com.rumofuture.nemo.model.dto.Response;
import com.rumofuture.nemo.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 关注业务API
 *
 * @author 王振琦
 * createAt: 2018/11/06
 * updateAt: 2019/01/08
 */
@RestController
@RequestMapping(value = "api")
public class FollowController {

    private final FollowService followService;

    @Autowired
    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @PostMapping(value = "follows")
    public Response<Follow> actionSaveFollow(@RequestBody Follow follow) {
        followService.saveFollow(follow);
        return new Response<>(follow);
    }

    @DeleteMapping(value = "follows/{id}")
    public Response<Follow> actionDeleteFollow(@PathVariable(value = "id") Integer id) {
        followService.deleteFollow(id);
        return new Response<>();
    }

    @GetMapping(value = "follows")
    public Response<Follow> actionQueryFollowByAuthorAndUser(
            @RequestParam(value = "authorId") Integer authorId, @RequestParam(value = "userId") Integer userId
    ) {
        Follow follow = followService.findFollowByAuthorAndUser(authorId, userId);
        return new Response<>(follow);
    }

    @GetMapping(value = "follows/author")
    public Response<List<User>> actionQueryFollowAuthorList(
            @RequestParam(value = "userId") Integer userId
    ) {
        List<User> authorList = followService.findFollowAuthorList(userId);
        return new Response<>(authorList);
    }

    @GetMapping(value = "follows/user")
    public Response<List<User>> actionQueryFollowerList(
            @RequestParam(value = "authorId") Integer authorId
    ) {
        List<User> followerList = followService.findFollowerList(authorId);
        return new Response<>(followerList);
    }
}
