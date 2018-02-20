package com.fr.bi;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

public class JobscriptScraperTest {

	private final String testItemPath = "C:\\Users\\al-drin.g.san.pablo\\git\\JobscriptScraper\\JobscriptScraper\\src\\main\\resources\\process\\BIDBMT50102_AuthMng\\BL\\BIDBMT50102BL01_UserAcntMCrt_0.1.item";
	private final String testItemPath2 = "C:\\Users\\al-drin.g.san.pablo\\git\\JobscriptScraper\\JobscriptScraper\\src\\main\\resources\\process\\BIDBMT50104_ItmMMng\\BL\\BIDBMT50104BL06_ItmCtgryMCrt_0.1.item";
	private final String testProcessPath = "C:\\Users\\jonelisa.l.miguel\\Desktop\\process";
	private final String testExcelPath = "C:\\Users\\jonelisa.l.miguel\\Desktop\\dumpexcel.xlsx";
	
	@Test
	@Ignore
	public void testListAllParams() {
		System.out.println( CustomUtility.listAllParams(testItemPath) );
//		System.out.println( CustomUtility.listAllParams(testItemPath2) );
	}

	@Test
	@Ignore
	public void testGetAllPaths() {
		List<File> list = CustomUtility.getAllPaths(testProcessPath);
		assertNotNull(list);

		int count = 0;
		for (File item : list) {
			System.out.println("" + count++ + ": " + item.getAbsolutePath());
		}
	}

	@Test
	public void testRun() {
		JobscriptScraper.run(testProcessPath,testExcelPath);
//		JobscriptScraper.run("C:\\Users\\al-drin.g.san.pablo\\Documents\\03 Workspace\\STS\\JobscriptScraper\\src\\main\\resources\\process\\BIDBMT50104_ItmMMng\\BL\\BIDBMT50104BL06_ItmCtgryMCrt_0.1.item");
	}

	@Test
	@Ignore
	public void testExtractArguments() {
		CustomUtility.extractArguments(
				"LoadSQLFile.getSqlString(\"BIDBMT50901BL21_del_01\", OperationDate.cast(context.OpDate), \"PROCESS_GROUP_ID\", context.ProcessGroupID)", testProcessPath);
		CustomUtility.extractArguments(
				"LoadSQLFile.getSqlString(\"BIDBMT50901BL21_del_01\", OperationDate.cast(context.OpDate), \"PROCESS_GROUP_ID\", context.ProcessGroupID, \"LOT_NUM\", context.lotNum)", testProcessPath);
		CustomUtility.extractArguments(
				"LoadSQLFile.getSqlString(\"BIDBMT50104BL06_del_01\", OperationDate.cast(context.OpDate),  \"LOT_NUM\", (String)globalMap.get(\"LOT_NUM\"))", testProcessPath);
	}

	@Test
	@Ignore
	public void testWriteToFile() {
		try {
			CustomUtility.writeToFile(null, testExcelPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
