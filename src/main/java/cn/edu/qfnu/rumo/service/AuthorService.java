package cn.edu.qfnu.rumo.service;

import cn.edu.qfnu.rumo.model.domain.User;

import java.util.List;

/**
 * 漫画册作者业务逻辑规范
 *
 * @author 王振琦
 * createAt: 2018/11/06
 * updateAt: 2018/11/06
 */
public interface AuthorService {

    /**
     * 根据用户ID查询用户对象
     *
     * @param id 用户ID
     * @return 用户对象
     */
    User findAuthorContainsBookList(Integer id);

    /**
     * 获取漫画册作者对象集合
     *
     * @return 漫画册作者对象集合
     */
    List<User> findAuthorList();

    /**
     * 获取首页的漫画家数据集合
     *
     * @return 漫画家数据集合
     */
    List<User> findAuthorListForIndex();

    /**
     * 查找按粉丝数量排序后的讲师对象集合
     *
     * @param index 分页索引
     * @return 讲师数据集合
     */
    List<User> findLecturerListByRank(int index);
}
