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
package com.theoriginalbit.moarperipherals.common.util;

import com.theoriginalbit.moarperipherals.MoarPeripherals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Code modified from OpenCCSensors: https://github.com/Cloudhunter/OpenCCSensors
 *
 * @author theoriginalbit
 * @since 13/11/14
 */
public final class ResourceExtractingUtil {

    /**
     * Copies a file from source to destination
     */
    private static void copyFile(File source, File destination) throws IOException {
        FileInputStream inputStream = new FileInputStream(source);
        FileOutputStream outputStream = new FileOutputStream(destination);
        FileChannel sourceChannel = inputStream.getChannel();
        FileChannel targetChannel = outputStream.getChannel();
        sourceChannel.transferTo(0, sourceChannel.size(), targetChannel);
        sourceChannel.close();
        targetChannel.close();
        inputStream.close();
        outputStream.close();
    }

    /**
     * Recursively copies a directory from source to destination including
     * all the files within the folders
     */
    public static void copyDirectory(File source, File destination) throws IOException {
        if (!source.isDirectory()) {
            throw new IllegalArgumentException("Source (" + source.getPath() + ") must be a directory.");
        }

        if (!source.exists()) {
            throw new IllegalArgumentException("Source directory (" + source.getPath() + ") doesn't exist.");
        }

        if (destination.mkdirs()) {
            File[] files = source.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        copyDirectory(file, new File(destination, file.getName()));
                    } else {
                        String name = file.getName();
                        // allows for .lua extensions in development env, but removes for play env
                        if (name.endsWith(".lua")) {
                            name = name.substring(0, name.length() - 4);
                        }
                        copyFile(file, new File(destination, name));
                    }
                }
            }
        }
    }

    public static void copy(File source, File destination) throws IOException {
        if (source.isDirectory()) {
            copyDirectory(source, destination);
        } else {
            copyFile(source, destination);
        }
    }

    /**
     * Extract a zip in sourceFolder into destFolder
     */
    public static void extractZipToLocation(File zipFile, String sourceFolder, String destFolder) {
        try {
            sourceFolder = sourceFolder.substring(1) + "/";
            final File destFile = new File(MoarPeripherals.proxy.getBase(), destFolder);
            final String destinationName = destFile.getAbsolutePath();
            byte[] buf = new byte[1024];
            final ZipInputStream inputStream = new ZipInputStream(new FileInputStream(zipFile));
            ZipEntry zipEntry;
            while ((zipEntry = inputStream.getNextEntry()) != null) {
                // for each entry to be extracted
                String zipEntryName = zipEntry.getName();

                if (!zipEntryName.startsWith(sourceFolder)) {
                    continue;
                }

                String entryName = destinationName + zipEntryName.substring(Math.min(zipEntryName.length(), sourceFolder.length() - 1));
                entryName = entryName.replace('/', File.separatorChar);
                entryName = entryName.replace('\\', File.separatorChar);
                int n;
                FileOutputStream fileoutputstream;
                // allows for .lua extensions in development env, but removes for play env
                File newFile = new File(entryName);
                if (zipEntry.isDirectory()) {
                    if (!newFile.mkdirs()) {
                        break;
                    }
                    continue;
                }

                if (entryName.endsWith(".lua")) {
                    entryName = entryName.substring(0, entryName.length() - 4);
                }
                fileoutputstream = new FileOutputStream(entryName);

                while ((n = inputStream.read(buf, 0, 1024)) > -1) {
                    fileoutputstream.write(buf, 0, n);
                }

                fileoutputstream.close();
                inputStream.closeEntry();
            }

            inputStream.close();
        } catch (Exception e) {
            LogUtil.warn("Error while extracting Lua files. Peripheral may not auto-mount Lua files! Exception " +
                    "follows.");
            e.printStackTrace();
        }
    }
}
