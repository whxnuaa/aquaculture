package com.jit.aquaculture.rbac;


import com.jit.aquaculture.domain.user.Permission;
import com.jit.aquaculture.domain.user.Role;
import com.jit.aquaculture.mapper.user.PermissionMapper;
import com.jit.aquaculture.mapper.user.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 最核心的地方，就是提供某个资源对应的权限定义，即getAttributes方法返回的结果。
 * 此类在初始化时，应该取到所有资源及其对应角色的定义。
 * 这个用来加载资源与权限的全部对应关系的，并提供一个通过资源获取所有权限的方法。
 * 首先，这里也是模拟了从数据库中获取信息。其中loadResourceDefine方法不是必须的,
 * 这个只是加载所有的资源与权限的对应关系并缓存起来，避免每次获取权限都访问数据库（提高性能），
 * 然后getAttributes根据参数（被拦截url）返回权限集合。
 * 这种缓存的实现其实有一个缺点，因为loadResourceDefine方法是放在构造器上调用的，而这个类的实例化只在web服务器启动时调用一次,
 * 那就是说loadResourceDefine方法只会调用一次,
 * 如果资源和权限的对应关系在启动后发生了改变，那么缓存起来的就是脏数据，现在这里使用的就是缓存数据,
 * 那就会授权错误了。但如果资源和权限对应关系是不会改变的，这种方法性能会好很多。
 * 在getAttributes方法里面调用dao（这个是加载完，后来才会调用的，所以可以使用dao） ，
 * 通过被拦截url获取数据库中的所有权限，封装成Collection<ConfigAttribute>返回就行了。（灵活、简单）
 */
@Service
public class MyInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {
//    @Override
//    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
//        final HttpServletRequest request = ((FilterInvocation) object).getRequest();
//        System.out.println("MyInvocationSecurityMetadataSourceService request.url: " + request.getRequestURI() + " request.method:" + request.getMethod());
//        Set<ConfigAttribute> allAttributes = new HashSet<>();
//        ConfigAttribute configAttribute = new MyConfigAttribute(request);
//        allAttributes.add(configAttribute);
//        System.out.println("MyInvocationSecurityMetadataSourceService allAttributes: " + allAttributes);
//        return allAttributes;
//    }
//
//    @Override
//    public Collection<ConfigAttribute> getAllConfigAttributes() {
//        return null;
//    }
//
//    @Override
//    public boolean supports(Class<?> aClass) {
//        return true;
//    }

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private RoleMapper roleMapper;

    private static Map<String,Collection<ConfigAttribute>> resourceMap = null;

    //加载权限表中的权限
    private void loadResourceDefine(){
        resourceMap = new HashMap<>();
        System.out.println("MyInvocationSecurityMetadataSourceService.loadResourcesDefine()--------------开始加载资源列表数据--------");
        List<Role> roles = roleMapper.findAllRoles();
        for (Role role : roles) {
            List<Permission> permissions = permissionMapper.findAllPerminssionsByRoleId(role.getId());
            for (Permission permission : permissions) {
                Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
                ConfigAttribute configAttribute = new SecurityConfig(role.getName());
                configAttributes.add(configAttribute);
                String url = permission.getUrl();
                if (resourceMap.containsKey(url)) {
                    Collection<ConfigAttribute> value = resourceMap.get(url);
                    value.add(configAttribute);
                    resourceMap.put(url,value);
                }else {
                    Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
                    atts.add(configAttribute);
                    resourceMap.put(url,atts);
                }
            }
        }
        System.out.println("MyInvocationSecurityMetadataSourceService map : "+ resourceMap);
    }

    //此方法是为了判定用户请求的url 是否在权限表中，即根据请求的资源地址，获取它所拥有的权限
    // 如果在权限表中，则返回给 decide 方法，用来判定用户是否有此权限。如果不在权限表中则放行。
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        if (resourceMap == null) loadResourceDefine();
        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
        AntPathRequestMatcher matcher;

        Iterator<String> iterator = resourceMap.keySet().iterator();
        while ( iterator.hasNext() ){
            String resUrl = iterator.next();
            matcher = new AntPathRequestMatcher(resUrl);
            if (matcher.matches(request)){
                return resourceMap.get(resUrl);
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}

