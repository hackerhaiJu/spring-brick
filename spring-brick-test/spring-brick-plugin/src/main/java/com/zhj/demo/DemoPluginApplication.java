package com.zhj.demo;

import com.gitee.starblues.bootstrap.SpringPluginBootstrap;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p> </p>
 *
 * @author haijun
 * @version 1.0.0
 * @email "mailto:zhonghaijun@zhxx.com"
 * @date 2024.07.19 15:53
 * @since 1.0.0
 */
@SpringBootApplication
public class DemoPluginApplication extends SpringPluginBootstrap {

    public static void main(String[] args) {
        DemoPluginApplication pluginApplication = new DemoPluginApplication();
        pluginApplication.run(args);
    }

}
