package com.fr.bi;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JobscriptScraper {

	public static void run(String sourcePath, String excelPath) {
System.out.println("###START###");
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
						Map<String,String> iMap = CustomUtility.extractArguments(str, i.getName());
						Item nItem = new Item(i.getAbsolutePath(), str);
						nItem.setSfcId(iMap.get(Constants.KEY_SFC_ID));
						iMap.remove(Constants.KEY_SFC_ID);
						nItem.setProcessId(iMap.get(Constants.KEY_PROC_ID));
						iMap.remove(Constants.KEY_PROC_ID);
						nItem.setArgumentMap(iMap);
						itemList.add(nItem);
//						System.out.println(nItem);
					}
				}

				if (!itemList.isEmpty()) {
					// write to excel
					CustomUtility.writeToFile(itemList,excelPath);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
System.out.println("###END###");
	}

}
