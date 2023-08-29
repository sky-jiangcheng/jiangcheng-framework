package com.jiangchengframework;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

import com.jiangchengframework.aop.AdviceChain;
import com.jiangchengframework.aop.ProxyAdvisor;

/**
 * v1 代理类创建器
 * v2 修改为链式通知调用
 */
public final class ProxyCreator {
    /**
     * 创建代理类
     * v1
     */
    /*public static Object createProxy(Class<?> targetClass, ProxyAdvisor proxyAdvisor){
        return Enhancer.create(targetClass, (MethodInterceptor) (target, method, args, proxy) ->
                proxyAdvisor.doProxy(target, targetClass, method, args, proxy));
    }
*/


    /**
     * 创建代理类
     * v2
     */
    public static Object createProxy(Class<?> targetClass, List<ProxyAdvisor> proxyList) {
        return Enhancer.create(targetClass, new AdviceMethodInterceptor(targetClass, proxyList));
    }

    /**
     * cglib MethodInterceptor实现类
     */
    private static class AdviceMethodInterceptor implements MethodInterceptor {

        /**
         * 目标类
         */
        private final Class<?> targetClass;

        /**
         * 代理通知列表
         */
        private List<ProxyAdvisor> proxyList;

        public AdviceMethodInterceptor(Class<?> targetClass, List<ProxyAdvisor> proxyList) {
            this.targetClass = targetClass;
            this.proxyList = proxyList;
        }

        @Override
        public Object intercept(Object target, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            return new AdviceChain(targetClass, target, method, args, proxy, proxyList).doAdviceChain();
        }
    }
}
