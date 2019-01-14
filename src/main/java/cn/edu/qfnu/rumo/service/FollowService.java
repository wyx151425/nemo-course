package cn.edu.qfnu.rumo.service;

import cn.edu.qfnu.rumo.model.domain.Follow;
import cn.edu.qfnu.rumo.model.domain.User;

import java.util.List;

/**
 * 关注业务逻辑规范
 *
 * @author 王振琦
 * createAt: 2018/11/06
 * updateAt: 2019/01/08
 */
public interface FollowService {
    /**
     * 保存用户关注
     *
     * @param follow 用户关注对象
     */
    void saveFollow(Follow follow);

    /**
     * 删除用户关注
     *
     * @param id 用户关注对象的ID
     */
    void deleteFollow(Integer id);

    /**
     * 根据漫画家和用户获取关注关系对象
     *
     * @param authorId 漫画家ID
     * @param userId   用户ID
     * @return 关注关系对象
     */
    Follow findFollowByAuthorAndUser(Integer authorId, Integer userId);

    /**
     * 获取漫画册作者集合
     *
     * @param userId 粉丝ID
     * @return 漫画册作者集合
     */
    List<User> findFollowAuthorList(Integer userId);

    /**
     * 获取粉丝数据集合
     *
     * @param authorId 作者ID
     * @return 粉丝数据集合
     */
    List<User> findFollowerList(Integer authorId);
}
