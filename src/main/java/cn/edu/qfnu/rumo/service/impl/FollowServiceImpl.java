package cn.edu.qfnu.rumo.service.impl;

import cn.edu.qfnu.rumo.model.domain.Follow;
import cn.edu.qfnu.rumo.model.domain.User;
import cn.edu.qfnu.rumo.repository.FollowRepository;
import cn.edu.qfnu.rumo.repository.UserRepository;
import cn.edu.qfnu.rumo.service.FollowService;
import cn.edu.qfnu.rumo.util.Constant;
import cn.edu.qfnu.rumo.util.Generator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 关注业务逻辑实现
 *
 * @author 王振琦
 * createAt: 2018/11/06
 * updateAt: 2019/01/08
 */
@Service
public class FollowServiceImpl implements FollowService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    @Autowired
    public FollowServiceImpl(FollowRepository followRepository, UserRepository userRepository) {
        this.followRepository = followRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveFollow(Follow follow) {
        follow.setObjectId(Generator.getObjectId());
        follow.setStatus(Constant.Status.GENERAL);
        followRepository.save(follow);
        User author = userRepository.findUserById(follow.getAuthor().getId());
        int follower = author.getFollower();
        author.setFollower(++follower);
        userRepository.update(author);
        User user = userRepository.findUserById(follow.getUser().getId());
        int followed = user.getFollow();
        user.setFollow(++followed);
        userRepository.update(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteFollow(Integer id) {
        Follow follow = followRepository.findFollowById(id);
        followRepository.delete(id);
        User author = userRepository.findUserById(follow.getAuthorId());
        int follower = author.getFollower();
        author.setFollower(--follower);
        userRepository.update(author);
        User user = userRepository.findUserById(follow.getUserId());
        int followed = user.getFollow();
        user.setFollow(--followed);
        userRepository.update(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Follow findFollowByAuthorAndUser(Integer authorId, Integer userId) {
        return followRepository.findFollowByAuthorIdAndUserId(authorId, userId);
    }

    @Override
    public List<User> findFollowAuthorList(Integer userId) {
        return followRepository.findFollowAuthorList(userId);
    }

    @Override
    public List<User> findFollowerList(Integer authorId) {
        return followRepository.findFollowerList(authorId);
    }
}
