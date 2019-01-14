package cn.edu.qfnu.rumo.repository;

import cn.edu.qfnu.rumo.model.domain.Follow;
import cn.edu.qfnu.rumo.model.domain.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户关注数据仓库
 *
 * @author 王振琦
 * createAt: 2018/11/06
 * updateAt: 2019/01/08
 */
@Repository(value = "followRepository")
public interface FollowRepository {
    /**
     * 保存用户关注信息对象
     *
     * @param follow 用户关注信息对象
     */
    void save(Follow follow);

    /**
     * 根据ID删除用户关注信息对象
     *
     * @param id 用户关注信息对象的ID
     */
    void delete(Integer id);

    /**
     * 根据ID获取用户关注信息对象
     *
     * @param id 用户关注信息对象
     * @return 用户关注信息对象
     */
    Follow findFollowById(Integer id);

    /**
     * 根据漫画家ID和用户ID获取关注关系对象
     *
     * @param authorId 漫画家ID
     * @param userId   用户ID
     * @return 关注关系对象
     */
    Follow findFollowByAuthorIdAndUserId(
            @Param(value = "authorId") Integer authorId, @Param(value = "userId") Integer userId
    );

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
