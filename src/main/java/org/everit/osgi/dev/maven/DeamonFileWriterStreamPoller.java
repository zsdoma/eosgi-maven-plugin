package org.everit.osgi.dev.maven;

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

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DeamonFileWriterStreamPoller implements Closeable {

    private class PollerRunnable implements Runnable {

        @Override
        public void run() {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            try {
                while (!closed && ((line = bufferedReader.readLine()) != null)) {
                    fout.write(line.getBytes());
                    fout.write("\n".getBytes());
                }
                inputStream.close();
                closed = true;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    private boolean closed;

    private File file;

    private FileOutputStream fout;

    private InputStream inputStream;

    public DeamonFileWriterStreamPoller(final InputStream inputStream, final File file) {
        this.inputStream = inputStream;
        this.file = file;
    }

    @Override
    public void close() throws IOException {
        if (!closed) {
            closed = true;
            inputStream.close();
            fout.close();
        }

    }

    public void start() throws IOException {
        fout = new FileOutputStream(file);
        new Thread(new PollerRunnable()).start();
    }
}