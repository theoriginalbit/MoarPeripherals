/**
 * Copyright 2014-2015 Joshua Asbury (@theoriginalbit)
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.theoriginalbit.moarperipherals.common.mount;

import com.theoriginalbit.framework.peripheral.interfaces.IPFMount;
import com.theoriginalbit.moarperipherals.common.reference.ModInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author theoriginalbit
 * @since 13/11/14
 */
public class MountMoarP implements IPFMount {
    private static final File BASE_DIR = new File(ModInfo.EXTRACTED_LUA_PATH);
    private static final String LUA_NAME = "moarp";

    @Override
    public String getMountLocation() {
        return LUA_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean exists(String path) throws IOException {
        return new File(BASE_DIR, path).exists();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDirectory(String path) throws IOException {
        return new File(BASE_DIR, path).isDirectory();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void list(String path, List<String> contents) throws IOException {
        final File[] files = new File(BASE_DIR, path).listFiles();
        if (files != null) {
            for (final File f : files) {
                contents.add(f.getName());
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getSize(String path) throws IOException {
        return new File(BASE_DIR, path).length();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InputStream openForRead(String path) throws IOException {
        return new FileInputStream(new File(BASE_DIR, path));
    }

}
