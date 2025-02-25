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

package com.gitee.starblues.spring.environment;

import java.util.function.BiConsumer;

/**
 * 空的配置信息提供者
 *
 * @author starBlues
 * @version 3.0.3
 */
public class EmptyEnvironmentProvider implements EnvironmentProvider{


    @Override
    public Object getValue(String name) {
        return null;
    }

    @Override
    public String getString(String name) {
        return null;
    }

    @Override
    public Integer getInteger(String name) {
        return null;
    }

    @Override
    public Long getLong(String name) {
        return null;
    }

    @Override
    public Double getDouble(String name) {
        return null;
    }

    @Override
    public Float getFloat(String name) {
        return null;
    }

    @Override
    public Boolean getBoolean(String name) {
        return null;
    }

    @Override
    public EnvironmentProvider getByPrefix(String prefix) {
        return new EmptyEnvironmentProvider();
    }

    @Override
    public void forEach(BiConsumer<String, Object> action) {

    }
}
