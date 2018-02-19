package com.fr.bi;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JobscriptScraper {

	public static void run(String sourcePath, String excelPath) {

		List<Item> itemList = null;
		List<File> fileList = null;

		try {
			fileList = CustomUtility.getAllPaths(sourcePath);
			System.out.println("fileList size: " + fileList.size());

			if (null != fileList && fileList.size() > 0) {
				itemList = new ArrayList<Item>();
				for (File i : fileList) {
					// getParams
					for ( String str : CustomUtility.listAllParams(i.getAbsolutePath())) {
						Item nItem = new Item(i.getAbsolutePath(), str);
						nItem.setArgumentMap(CustomUtility.extractArguments(str));
						itemList.add(nItem);
					}
//					System.out.println(nItem);
				}

				if (!itemList.isEmpty()) {
					// write to excel
					CustomUtility.writeToFile(itemList,excelPath);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
