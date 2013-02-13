package org.everit.osgi.dev.maven.util;

/*
 * Copyright (c) 2011, Everit Kft.
 *
 * All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;

public class Java7SymbolicLinkUtil {

    public static boolean createSymbolicLinks(final Map<File, File> sourceAndTargetFileMap,
            Map<String, Artifact> artifactMap, Log log)
            throws MojoExecutionException {
        try {
            for (Entry<File, File> entry : sourceAndTargetFileMap.entrySet()) {
                Files.createSymbolicLink(entry.getValue().toPath(), entry.getKey().toPath());
            }
            return true;
        } catch (FileSystemException e) {
            log.warn("Unable to create symbolic links with java 7 api.", e);
            return false;
        } catch (IOException e) {
            throw new MojoExecutionException("Unable to create symbolic links", e);
            
        }
    }
}
