package com.jit.aquaculture.rbac;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 *
 * AccessDecisionManager在Spring security中是很重要的。
 *
 * 在验证部分简略提过了，所有的Authentication实现需要保存在一个GrantedAuthority对象数组中。 这就是赋予给主体的权限。
 * GrantedAuthority对象通过AuthenticationManager 保存到Authentication对象里，然后从AccessDecisionManager读出来，进行授权判断。
 *
 * Spring Security提供了一些拦截器，来控制对安全对象的访问权限，例如方法调用或web请求。
 * 一个是否允许执行调用的预调用决定，是由AccessDecisionManager实现的。 这个 AccessDecisionManager
 * 被AbstractSecurityInterceptor调用， 它用来作最终访问控制的决定。
 * 这个AccessDecisionManager接口包含三个方法：
 *
 * void decide(Authentication authentication, Object secureObject, List<ConfigAttributeDefinition> config) throws AccessDeniedException;
 * boolean supports(ConfigAttribute attribute);
 * boolean supports(Class clazz);
 *
 * 从第一个方法可以看出来，AccessDecisionManager使用方法参数传递所有信息，这好像在认证评估时进行决定。
 * 特别是，在真实的安全方法期望调用的时候，传递安全Object启用那些参数。 比如，让我们假设安全对象是一个MethodInvocation。
 * 很容易为任何Customer参数查询MethodInvocation，
 * 然后在AccessDecisionManager里实现一些有序的安全逻辑，来确认主体是否允许在那个客户上操作。
 * 如果访问被拒绝，实现将抛出一个AccessDeniedException异常。
 *
 * 这个 supports(ConfigAttribute) 方法在启动的时候被AbstractSecurityInterceptor调用，来决定AccessDecisionManager
 * 是否可以执行传递ConfigAttribute。
 *
 * supports(Class)方法被安全拦截器实现调用，包含安全拦截器将显示的AccessDecisionManager支持安全对象的类型。
 */
@Service
public class MyAccessDecisionManager  implements AccessDecisionManager {
    /**
     * decide 方法是判定是否拥有权限的决策方法，
     * @param authentication 是CustomUserService中循环添加到 GrantedAuthority 对象中的权限信息集合.
     * @param object 包含客户端发起的请求的request信息，可转换为 HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
     * @param configAttributes 为MyInvocationSecurityMetadataSource的getAttributes(Object object)这个方法返回的结果，
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     * 此方法是为了判定用户请求的url 是否在权限表中，如果在权限表中，则返回给 decide 方法，
     * 用来判定用户是否有此权限。如果不在权限表中则放行。
     */
    @Override
        public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        //将接口的method方法也作为是否可以访问的依据
        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
        System.out.println("MyAccessDecisionManager request.url: "+ request.getRequestURI() + " MyAccessDecisionManager request.method: "  + request.getMethod());
        String url, method;
        if("anonymousUser".equals(authentication.getPrincipal())
                || matchers("/images/**", request)
                || matchers("/js/**", request)
                || matchers("/css/**", request)
                || matchers("/fonts/**", request)
                || matchers("/", request)
                || matchers("/index.html", request)
                || matchers("/favicon.ico", request)
                || matchers("/login",request)){
            return;
        }else {
            for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
                System.out.println("MyAccessDecisionManager grantedAuthority " + authentication.getAuthorities());
                if (grantedAuthority instanceof MyGrantedAuthority) {
                    MyGrantedAuthority urlGrantedAuthority = (MyGrantedAuthority) grantedAuthority;
                    url = urlGrantedAuthority.getUrl();
                    System.out.println("url: " + url);
                    method = urlGrantedAuthority.getMethod();
                    System.out.println("method: " + method);
                    if (matchers(url, request)) {
                        //当权限表权限的method为ALL时表示拥有此路径的所有请求方式权利
                        if (method.equals(request.getMethod()) || "ALL".equals(method)) {
                            return;
                        }
                    }
                }
            }
        }
        throw new AccessDeniedException("Access Denied");

//        if (null == configAttributes || configAttributes.size()<=0){
//            return;
//        }
//        Iterator<ConfigAttribute> iterator = configAttributes.iterator();
//        while (iterator.hasNext()){
//            ConfigAttribute ca = iterator.next();
//            String needRole = ca.getAttribute();
//            System.out.println("needRole " + needRole + ", authentication.getAuthorities() " + authentication.getAuthorities());
//            for (GrantedAuthority ga: authentication.getAuthorities()){
//                //authentication 为在注释1 中循环添加到 GrantedAuthority 对象中的权限信息集合
//                if (needRole.trim().equals(ga.getAuthority())){
//                    return;
//                }
//            }
//        }
//        throw new AccessDeniedException("Access Denied");
    }

    /**
     * 启动时候被AbstractSecurityInterceptor调用，决定AccessDecisionManager是否可以执行传递ConfigAttribute。
     * @param configAttribute
     * @return
     */
    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    /**
     * 被安全拦截器实现调用，包含安全拦截器将显示的AccessDecisionManager支持安全对象的类型
     * @param aClass
     * @return
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

    private boolean matchers(String url,HttpServletRequest request){
        AntPathRequestMatcher matcher = new AntPathRequestMatcher(url);
        if (matcher.matches(request)){
            return true;
        }
        return false;
    }
}
