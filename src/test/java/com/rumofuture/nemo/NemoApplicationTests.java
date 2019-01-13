package com.rumofuture.nemo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NemoApplicationTests {

    @Test
    public void contextLoads() {
    }

//    @Test
//    public void addPermission() {
//        Permission permission1 = new Permission();
//        permission1.setObjectId(Generator.getObjectId());
//        permission1.setStatus(Constant.Status.GENERAL);
//        permission1.setRole(Constant.Roles.USER);
//        permission1.setCode(Constant.Permissions.COMMON);
//        permissionRepository.save(permission1);
//
//        Permission permission2 = new Permission();
//        permission2.setObjectId(Generator.getObjectId());
//        permission2.setStatus(Constant.Status.GENERAL);
//        permission2.setRole(Constant.Roles.LECTURER);
//        permission2.setCode(Constant.Permissions.CREATE_COURSE);
//        permissionRepository.save(permission2);
//
//        Permission permission3 = new Permission();
//        permission3.setObjectId(Generator.getObjectId());
//        permission3.setStatus(Constant.Status.GENERAL);
//        permission3.setRole(Constant.Roles.LECTURER);
//        permission3.setCode(Constant.Permissions.OBTAIN_FOLLOWER);
//        permissionRepository.save(permission3);
//
//        Permission permission4 = new Permission();
//        permission4.setObjectId(Generator.getObjectId());
//        permission4.setStatus(Constant.Status.GENERAL);
//        permission4.setRole(Constant.Roles.ADMINISTRATOR);
//        permission4.setCode(Constant.Permissions.APPROVE_APPLICATION);
//        permissionRepository.save(permission4);
//    }
}
