/**
* The MIT License (MIT)
* 
* Copyright (c) 2015 CS56
* 
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:
* 
* The above copyright notice and this permission notice shall be included in all
* copies or substantial portions of the Software.
* 
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
* SOFTWARE.
 */

package edu.smc.mediacommons.modules;

import java.io.File;

public class SystemModule {

    public static int getCores() {
        return Runtime.getRuntime().availableProcessors();
    }
    
    public static long getFreeMemory() {
        return Runtime.getRuntime().freeMemory();
    }
    
    public static long getMaxMemory() {
        long maxMemory = Runtime.getRuntime().maxMemory();
        return maxMemory == Long.MAX_VALUE ? maxMemory : maxMemory;
    }
    
    public static long getTotalMemory() {
        return Runtime.getRuntime().totalMemory();
    }
    
    public static void listRoots() {
        File[] roots = File.listRoots();

        for (File root : roots) {
             System.out.println("File system root: " + root.getAbsolutePath());
             System.out.println("Total space (bytes): " + root.getTotalSpace());
             System.out.println("Free space (bytes): " + root.getFreeSpace());
             System.out.println("Usable space (bytes): " + root.getUsableSpace());
        }
    }
}