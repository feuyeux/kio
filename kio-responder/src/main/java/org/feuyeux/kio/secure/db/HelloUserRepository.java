package org.feuyeux.kio.secure.db;

import org.feuyeux.kio.pojo.secure.HelloUser;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

import static org.feuyeux.kio.pojo.secure.HelloRole.ADMIN;
import static org.feuyeux.kio.pojo.secure.HelloRole.USER;

/**
 * @author feuyeux@gmail.com
 */
@Repository
public class HelloUserRepository {
    private Map<String, HelloUser> userTable = new HashMap<>();

    @PostConstruct
    public void init() {
        HelloUser admin = HelloUser.builder().userId("9527").password("KauNgJikCeo").role(ADMIN).build();
        userTable.put("9527", admin);
        HelloUser user = HelloUser.builder().userId("0000").password("Zero4").role(USER).build();
        userTable.put("0000", user);
    }

    public HelloUser retrieve(String userId) {
        return userTable.get(userId);
    }
}
