package com.rumofuture.nemo.service.impl;

import com.rumofuture.nemo.context.exception.NemoException;
import com.rumofuture.nemo.model.domain.Permission;
import com.rumofuture.nemo.model.domain.User;
import com.rumofuture.nemo.model.dto.UserPwd;
import com.rumofuture.nemo.repository.PermissionRepository;
import com.rumofuture.nemo.repository.UserRepository;
import com.rumofuture.nemo.service.UserService;
import com.rumofuture.nemo.util.Constant;
import com.rumofuture.nemo.util.Generator;
import com.rumofuture.nemo.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户业务逻辑类
 *
 * @author 王振琦
 * createAt: 2018/08/01
 * updateAt: 2018/10/10
 */
@Service(value = "userService")
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PermissionRepository permissionRepository) {
        this.userRepository = userRepository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public User login(User user) {
        User targetUser = userRepository.findUserByMobilePhoneNumber(user.getMobilePhoneNumber());
        if (null == targetUser) {
            throw new NemoException(StatusCode.USER_UNREGISTER);
        } else {
            if (Constant.UserStatus.DISABLED == targetUser.getStatus()) {
                throw new NemoException(StatusCode.USER_DISABLED);
            } else {
                if (targetUser.getPassword().equals(user.getPassword())) {
                    List<Permission> permissionList = permissionRepository.findAllByRole(targetUser.getRole());
                    Map<String, Boolean> permissions = new HashMap<>();
                    for (Permission permission : permissionList) {
                        permissions.put(permission.getCode(), true);
                    }
                    targetUser.setPermissions(permissions);
                    return targetUser;
                } else {
                    throw new NemoException(StatusCode.USER_PASSWORD_ERROR);
                }
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User register(User user) {
        User targetUser = userRepository.findUserByMobilePhoneNumber(user.getMobilePhoneNumber());
        if (null == targetUser) {
            user.setObjectId(Generator.getObjectId());
            user.setStatus(Constant.UserStatus.USER);
            user.setAvatar("../images/default-avatar.jpg");
            user.setPortrait("../images/default-portrait.jpg");
            user.setGender("保密");
            user.setFollow(0);
            user.setFollower(0);
            user.setFavorite(0);
            user.setBook(0);
            user.setRole(Constant.Roles.USER);
            userRepository.save(user);
            return user;
        } else {
            throw new NemoException(StatusCode.USER_REGISTERED);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User update(User user) {
        userRepository.update(user);
        return userRepository.findUserById(user.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User updateEmail(User user) {
        User targetUser = userRepository.findUserById(user.getId());
        if (targetUser.getPassword().equals(user.getPassword())) {
            targetUser.setEmail(user.getEmail());
            userRepository.update(targetUser);
            return targetUser;
        } else {
            throw new NemoException(StatusCode.USER_PASSWORD_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User updatePassword(UserPwd userPwd) {
        User targetUser = userRepository.findUserById(userPwd.getId());
        if (targetUser.getPassword().equals(userPwd.getOldPassword())) {
            targetUser.setPassword(userPwd.getNewPassword());
            userRepository.update(targetUser);
            return targetUser;
        } else {
            throw new NemoException(StatusCode.USER_PASSWORD_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User updateUserAvatar(User user) {
        User targetUser = userRepository.findUserById(user.getId());
        targetUser.setAvatar(user.getAvatar());
        userRepository.update(targetUser);
        return targetUser;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User updateUserPortrait(User user) {
        User targetUser = userRepository.findUserById(user.getId());
        targetUser.setPortrait(user.getPortrait());
        userRepository.update(targetUser);
        return targetUser;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public User findUserContainsBookList(Integer id) {
        User user = userRepository.findUserById(id);
        user.getBookList();
        List<Permission> permissionList = permissionRepository.findAllByRole(user.getRole());
        Map<String, Boolean> permissions = new HashMap<>();
        for (Permission permission : permissionList) {
            permissions.put(permission.getCode(), true);
        }
        user.setPermissions(permissions);
        return user;
    }
}
