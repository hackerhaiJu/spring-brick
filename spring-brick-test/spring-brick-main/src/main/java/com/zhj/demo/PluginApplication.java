package com.zhj.demo;

import com.gitee.starblues.loader.launcher.SpringBootstrap;
import com.gitee.starblues.loader.launcher.SpringMainBootstrap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p> </p>
 *
 * @author haijun
 * @version x.x.x
 * @email "mailto:zhonghaijun@zhxx.com"
 * @date 2024.07.19 14:42
 * @since x.x.x
 */
@SpringBootApplication
public class PluginApplication implements SpringBootstrap {

    /**
     * Main
     *
     * @param args args
     * @since 1.0.0
     */
    public static void main(String[] args) {
        SpringMainBootstrap.launch(PluginApplication.class, args);
    }

    @Override
    public void run(String[] args) throws Exception {
        SpringApplication.run(PluginApplication.class, args);
    }
}
