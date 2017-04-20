package com.springboot.base.shiro;

import com.springboot.base.data.enmus.UserStatus;
import com.springboot.base.data.entity.UserInfo;
import com.springboot.base.service.UserInfoService;
import lombok.extern.log4j.Log4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by jay on 2017-4-11.
 */
@Log4j
public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserInfoService userInfoService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * 登录认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("登录认证开始-------------------");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        UserInfo user = userInfoService.getUser(token.getUsername(), UserStatus.DEFAULT.getIndex());
        if (user != null) {
            // 若存在，将此用户存放到登录认证info中，无需自己做密码对比，Shiro会为我们进行密码对比校验
            return new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), ByteSource.Util.bytes(user.getSalt() + user.getUsername()), getName());
        }
        return null;
    }

}
