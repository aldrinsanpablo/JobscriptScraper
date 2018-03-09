package com.fr.bi;

import java.io.File;
import java.io.FileFilter;

public class NonDirectoryFilter implements FileFilter {

	public boolean accept(File arg0) {
		return !arg0.isDirectory();
	}

}
