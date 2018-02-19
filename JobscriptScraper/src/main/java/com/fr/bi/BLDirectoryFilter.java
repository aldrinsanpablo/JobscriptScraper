package com.fr.bi;

import java.io.File;
import java.io.FileFilter;

public class BLDirectoryFilter implements FileFilter {

    public boolean accept(File arg0) {
	return arg0.isDirectory() && arg0.getName().equals("BL");
    }

}
