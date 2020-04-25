package org.feuyeux.kio.secure.db;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.feuyeux.kio.pojo.secure.HelloUser;
import org.feuyeux.kio.secure.TokenUtils;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

/**
 * @author feuyeux@gmail.com
 */
@Repository
public class HelloTokenRepository {
    Cache<String, HelloUser> accessTokenTable = CacheBuilder.newBuilder()
            .expireAfterWrite(TokenUtils.ACCESS_EXPIRE, TimeUnit.MINUTES).build();
    Cache<String, HelloUser> freshTokenTable = CacheBuilder.newBuilder()
            .expireAfterWrite(TokenUtils.REFRESH_EXPIRE, TimeUnit.DAYS).build();

    public void storeAccessToken(String tokenId, HelloUser user) {
        accessTokenTable.put(tokenId, user);
    }

    public void storeFreshToken(String tokenId, HelloUser user) {
        freshTokenTable.put(tokenId, user);
    }

    public HelloUser getAuthFromAccessToken(String tokenId) {
        return accessTokenTable.getIfPresent(tokenId);
    }

    public HelloUser getAuthFromRefreshToken(String tokenId) {
        return freshTokenTable.getIfPresent(tokenId);
    }

    public void deleteAccessToken(String tokenId) {
        accessTokenTable.invalidate(tokenId);
    }

    public void deleteFreshToken(String tokenId) {
        freshTokenTable.invalidate(tokenId);
    }
}
