/*
 *
 *  * | Licensed 未经许可不能去掉「OPENIITA」相关版权
 *  * +----------------------------------------------------------------------
 *  * | Author: xw2sy@163.com
 *  * +----------------------------------------------------------------------
 *
 *  Copyright [2024] [OPENIITA]
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * /
 */

package com.gitee.starblues.spring;

import com.gitee.starblues.loader.classloader.GenericClassLoader;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * 解决插件线程中的ClassLoader
 *
 * @author starBlues
 * @version 3.0.3
 */
public class ResolvePluginThreadClassLoader implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ResolveHandlerInterceptor()).addPathPatterns("/**");
    }

    private static class ResolveHandlerInterceptor implements HandlerInterceptor {

        private final ThreadLocal<ClassLoader> oldClassLoader = new ThreadLocal<>();

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            if(handler instanceof HandlerMethod){
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                ClassLoader classLoader = handlerMethod.getBeanType().getClassLoader();
                if(classLoader instanceof GenericClassLoader){
                    this.oldClassLoader.set(Thread.currentThread().getContextClassLoader());
                    Thread.currentThread().setContextClassLoader(classLoader);
                }
            }
            return true;
        }

        @Override
        public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
            try {
                ClassLoader classLoader = this.oldClassLoader.get();
                if(classLoader != null){
                    Thread.currentThread().setContextClassLoader(classLoader);
                }
            } finally {
                this.oldClassLoader.remove();
            }
        }
    }

}
