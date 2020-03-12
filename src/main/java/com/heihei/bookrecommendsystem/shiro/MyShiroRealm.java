package com.heihei.bookrecommendsystem.shiro;


import com.heihei.bookrecommendsystem.entity.UserDO;
import com.heihei.bookrecommendsystem.service.PrivilegeService;
import com.heihei.bookrecommendsystem.service.RoleService;
import com.heihei.bookrecommendsystem.service.UserService;
import com.heihei.bookrecommendsystem.util.RSAUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 *
 * MyShiroRealm
 * @Description 定制自己的Realm，定制自己的认证和授权逻辑
 * @author CHENZEJIA
 * @date 2019/12/17
 */
public class MyShiroRealm extends AuthorizingRealm {
    Logger logger = LoggerFactory.getLogger(MyShiroRealm.class);
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;
    @Autowired
    PrivilegeService privilegeService;
//    @Autowired
//    RoleService roleService;
//    @Autowired
//    PrivilegeService privilegeService;
    /*
     * @Description 授权方法，当使用到授权注解，或者前端shiro注解，都会执行来执行这个方法，可配置缓存来存储获取到的权限。
     *              查询数据库，用户以及其角色以及角色对应的权限
     * @Author CHENZEJIA
     * @Date 2019/12/17
     * @Param [principalCollection]
     * @return org.apache.shiro.authz.AuthorizationInfo
     **/
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//        logger.info("授权");
//        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
//        String userName = (String) principalCollection.getPrimaryPrincipal();
//        UserDO userDO = userService.getOnlyUserByUserName(userName);               //根据用户名查询数据库的用户
//        List<RoleDO> roles = roleService.selectRolesByUserId(userDO.getId()); //查询用户对应的角色
//        for (RoleDO role : roles) {                                             //遍历用户的角色查询角色对应的权限，addStringPermission，将全放放进去
//            simpleAuthorizationInfo.addRole(role.getName());
//            logger.info("roleName: " + role.getName());
//            //查询角色对应的权限
//            List<PrivilegeDO> privileges =  privilegeService.listPrivilegeByRoleId(role.getId());
//            for (PrivilegeDO privilege : privileges) {
//                simpleAuthorizationInfo.addStringPermission(privilege.getName());
//                logger.info("PrivilegeName: " + privilege.getName());
//            }
//        }
        return null;
    }
    /*
     * @Description 认证方法，在调用subject.login(token)时，会进行认证，主要查询用户是否存在以及密码是否正确
     * @Author CHENZEJIA
     * @Date 2019/12/17
     * @Param [authenticationToken]
     * @return org.apache.shiro.authc.AuthenticationInfo
     **/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.info("进入doGetAuthenticationInfo 认证方法");
        String inputUserName = (String) authenticationToken.getPrincipal();
        UserDO userDO = userService.getOneUserByUserName(inputUserName);       //根据用户名查询用户，判断其是否存在
        if(userDO == null) {
            throw new UnknownAccountException("用户不存在");
        }else{
            logger.info(userDO.toString());
        }
        //判断密码是否正确，并且将用户放入session缓存
        String password = RSAUtil.decrypt(userDO.getPassword(),RSAUtil.PRIVATE_KEY);
        logger.info("数据库密码解密的结果" + password);
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(inputUserName, password,getName());
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute("userSession", userDO);
        return simpleAuthenticationInfo;
    }
}
