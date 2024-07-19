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

package com.gitee.starblues.spring.web;

import com.gitee.starblues.core.descriptor.PluginDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

/**
 * 插件资源实现类.主要是对Spring中的抽象的Resource实现.
 * 功能: 主要是获取插件包中的文件资源。
 * @author starBlues
 * @version 3.0.0
 */
public class PluginResource implements Resource {

    private final static Logger log = LoggerFactory.getLogger(PluginResource.class);

    private ClassLoader classLoader = ClassUtils.getDefaultClassLoader();
    private final PluginDescriptor pluginDescriptor;

    private final long lastModified;
    private final String path;


    /**
     * 相对Classpath 路径
     * @param path 路径
     * @param pluginDescriptor pluginDescriptor
     */
    public PluginResource(String path, PluginDescriptor pluginDescriptor) {
        String pathToUse = StringUtils.cleanPath(path);
        if (pathToUse.startsWith("/")) {
            pathToUse = pathToUse.substring(1);
        }
        this.path = pathToUse;
        this.pluginDescriptor = pluginDescriptor;
        this.lastModified = System.currentTimeMillis();
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return this.classLoader.getResourceAsStream(this.path);
    }


    @Override
    public long contentLength() throws IOException {
        URL url = this.getURL();
        if (ResourceUtils.isFileURL(url)) {
            return this.getFile().length();
        }
        if(ResourceUtils.isJarURL(url)){
            URLConnection con = this.getURL().openConnection();
            return con.getContentLength();
        }
        return 0L;
    }

    @Override
    public long lastModified() throws IOException {
        return this.lastModified;
    }


    @Override
    public Resource createRelative(String relativePath) {
        throw new RuntimeException("This method is not supported");
    }

    @Override
    public String getFilename() {
        return StringUtils.getFilename(this.path);
    }

    @Override
    public String getDescription() {
        return this.pluginDescriptor.getDescription();
    }

    @Override
    public boolean exists() {
        try {
            URL url = this.getURL();
            if(url == null){
                return false;
            }
            if (ResourceUtils.isFileURL(url)) {
                return this.getFile().exists();
            }
            if (this.contentLength() >= 0) {
                return true;
            }
            InputStream is = this.getInputStream();
            is.close();
            return true;
        } catch (Exception e){
            log.debug(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public boolean isReadable() {
        try {
            URL url = this.getURL();
            if (ResourceUtils.isFileURL(url)) {
                File file = this.getFile();
                return (file.canRead() && !file.isDirectory());
            } else {
                return true;
            }
        } catch (Exception e) {
            log.debug(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public boolean isOpen() {
        return false;
    }



    @Override
    public File getFile() throws IOException {
        URL url = this.getURL();
        if (ResourceUtils.isJarURL(url)) {
            URL actualUrl = ResourceUtils.extractArchiveURL(url);
            return ResourceUtils.getFile(actualUrl, "Jar URL");
        } else {
            return ResourceUtils.getFile(url, this.getDescription());
        }
    }

    @Override
    public URL getURL() throws IOException {
        return this.classLoader.getResource(this.path);
    }

    @Override
    public URI getURI() throws IOException {
        URL url = this.getURL();
        try {
            return ResourceUtils.toURI(url);
        } catch (URISyntaxException ex) {
            throw new RuntimeException("Invalid URI [" + url + "]", ex);
        }
    }

}

