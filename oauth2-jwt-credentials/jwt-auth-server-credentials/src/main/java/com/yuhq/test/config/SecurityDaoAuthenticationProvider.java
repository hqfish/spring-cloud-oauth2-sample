package com.yuhq.test.config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 核心类，在该类里校验用户密码
 * client模式下：用于登录页登录/login
 * pasword模式下：用于/auth/token
 *
 * 注：如果配置了内存用户，则该类不生效
 */
public class SecurityDaoAuthenticationProvider extends DaoAuthenticationProvider {

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails,
                                                  UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        //这是用户输入的用户名和密码
        String username = authentication.getPrincipal().toString();// 登录账号
        String pawwd = authentication.getCredentials().toString();

        //这是通过UserDetailsService加载后返回的
        String sysUsername = userDetails.getUsername();
        String sysPassword = userDetails.getPassword();


//        Map<String, String> parameters = (Map<String, String>) authentication.getDetails();
//        AuthUser authUser = uaaUserService.findAuthUserByLoginCode(username);
//        /*
//         * if(authUser==null){ log.debug("重新加载[{}]用户的authUser信息",username); authUser =
//         * uaaUserService.findAuthUserByLoginCode(username); }
//         */
//        authUser.setDetails(parameters);
//
//        //beforeChecks(authUser, authentication);
//        /** checks方法 */
//        checks(authUser, userDetails, authentication);
//        /** check后方法 */
//        afterChecks(authUser, authentication);

    }
}
