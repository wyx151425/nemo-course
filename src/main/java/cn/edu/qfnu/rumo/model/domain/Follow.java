package cn.edu.qfnu.rumo.model.domain;

/**
 * 关注类
 *
 * @author 王振琦
 * createAt: 2018/11/06
 * updateAt: 2018/11/06
 */
public class Follow extends RumoEntity {
    /**
     * 被关注的漫画作者
     */
    private User author;
    /**
     * 关注漫画作者的用户
     */
    private User user;
    /**
     * 被关注的漫画作者的ID
     */
    private Integer authorId;
    /**
     * 关注漫画作者的用户的ID
     */
    private Integer userId;

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
