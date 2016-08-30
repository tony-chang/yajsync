/*
 * Copyright (C) 2016 Per Lundqvist
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.perlundq.yajsync.internal.session;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.github.perlundq.yajsync.attr.FileInfo;
import com.github.perlundq.yajsync.attr.Group;
import com.github.perlundq.yajsync.attr.RsyncFileAttributes;
import com.github.perlundq.yajsync.attr.User;
import com.github.perlundq.yajsync.internal.util.FileOps;

public class FileInfoTest {

    RsyncFileAttributes _fileAttrs =
            new RsyncFileAttributes(FileOps.S_IFREG | 0644, 0, 0, User.JVM_USER,
                                    Group.JVM_GROUP);
    RsyncFileAttributes _dirAttrs =
        new RsyncFileAttributes(FileOps.S_IFDIR | 0755, 0, 0, User.JVM_USER,
                                Group.JVM_GROUP);
    String _dotDirPathName = ".";
    FileInfoImpl _dotDir = new FileInfoImpl(".", ".".getBytes(), _dirAttrs);

    @Test
    public void testSortDotDirWithDotDirEqual()
    {
        assertTrue(_dotDir.equals(_dotDir));
        assertEquals(_dotDir.compareTo(_dotDir), 0);
    }

    @Test
    public void testSortDotDirBeforeNonDotDir()
    {
        String str = ".a";
        FileInfo f = new FileInfoImpl(str, str.getBytes(), _fileAttrs);
        assertFalse(_dotDir.equals(f));
        assertTrue(_dotDir.compareTo(f) == -1);
    }

    @Test
    public void testSortDotDirBeforeNonDotDir2()
    {
        String str = "...";
        FileInfoImpl f = new FileInfoImpl(str, str.getBytes(), _fileAttrs);
        assertFalse(_dotDir.equals(f));
        assertTrue(_dotDir.compareTo(f) == -1);
    }

    @Test
    public void testSortDotDirBeforeNonDotDir3()
    {
        String str = "...";
        FileInfoImpl f = new FileInfoImpl(str, str.getBytes(), _dirAttrs);
        assertFalse(_dotDir.equals(f));
        assertTrue(_dotDir.compareTo(f) == -1);
    }

    // test empty throws illegalargumentexception
    // test dot is always a directory

    /*
     * ./
     * ...
     * ..../
     * .a
     * a.
     * a..
     * ..a
     * .a.
     *
     * a.
     * a../
     * .a/
     * .a/b
     * b/
     * b/.
     * c
     * cc
     * c.c
     */

    @Test
    public void testSortDotDirBeforeNonDotDir4()
    {
        String str = "a.";
        FileInfoImpl f = new FileInfoImpl(str, str.getBytes(), _dirAttrs);
        assertFalse(_dotDir.equals(f));
        assertTrue(_dotDir.compareTo(f) == -1);
    }
}