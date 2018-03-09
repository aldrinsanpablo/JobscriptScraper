package com.fr.bi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class CustomUtility {

    public static Pattern PATTERN_ARGS = Pattern.compile(Constants.REGEX_ARGS);

    public static List<File> getAllPaths(String directoryPath) {
	List<File> returnList = new ArrayList<File>();
	DirectoryFilter myDirFilter = new DirectoryFilter();

	File firstLevel = new File(directoryPath);
	if (null != firstLevel && firstLevel.isDirectory()) {
	    File[] firstLevelFiles = firstLevel.listFiles(myDirFilter);

	    if (null != firstLevelFiles && firstLevelFiles.length > 0) {
		for (File item : firstLevelFiles) {
		    File[] secondLevel = item.listFiles(new BLDirectoryFilter());

		    for (File innerItem : secondLevel) {
			File[] thirdLevel = innerItem.listFiles(new FilenameFilter() {

			    public boolean accept(File dir, String name) {
				return name.matches(Constants.REGEX_ITEM);
			    }
			});

			for (File thirdLevelItem : thirdLevel) {
			    returnList.add(new File(thirdLevelItem.getAbsolutePath()));
			}

		    }
		}
	    }
	} else {
	    if (firstLevel.isFile() && firstLevel.getName().matches(Constants.REGEX_ITEM)) {
		returnList.add(new File(firstLevel.getAbsolutePath()));
	    }
	}
	return returnList;
    }

    /**
     * List all parameter in the path specified
     * 
     * @param absolutePath
     */
    public static List<String> listAllParams(String absolutePath) {

	List<String> returnList = new ArrayList<String>();
	File fXmlFile = new File(absolutePath);

	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder;

	try {
	    dBuilder = dbFactory.newDocumentBuilder();
	    Document doc = dBuilder.parse(fXmlFile);
	    // optional
	    doc.getDocumentElement().normalize();

	    XPathFactory xPathfactory = XPathFactory.newInstance();
	    XPath xpath = xPathfactory.newXPath();
	    XPathExpression expr = xpath
		    .compile("*[name()='talendfile:ProcessType']//*//elementParameter[@field='MEMO_SQL']//@value");
	    // returnStr = expr.evaluate(doc, XPathConstants.STRING).toString();

	    NodeList nodelist = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
	    System.out.println("nodes:" + nodelist.getLength());

	    for (int x = 0; x < nodelist.getLength(); x++) {
		returnList.add(nodelist.item(x).getNodeValue());
		System.out.println(nodelist.item(x));
	    }

	} catch (ParserConfigurationException e) {
	    e.printStackTrace();
	} catch (XPathExpressionException e) {
	    e.printStackTrace();
	} catch (SAXException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}

	return returnList;
    }

    public static void writeToFile(List<Item> itemList, String excelPath) throws IOException {

	if (null != itemList && !itemList.isEmpty()) {
	    File excelFile = null;
	    XSSFWorkbook workbook = null;
	    FileOutputStream fileOut = null;
	    Row row = null;
	    Cell cell = null;

	    try {
		excelFile = new File(excelPath);
		workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Jobscript");

		/* Row 0 - Header START */
		row = sheet.createRow(0);
		cell = row.createCell(0);
		cell.setCellType(CellType.STRING);
		cell.setCellValue("Count");
		cell = row.createCell(1);
		cell.setCellType(CellType.STRING);
		cell.setCellValue("SFC ID");
		cell = row.createCell(2);
		cell.setCellType(CellType.STRING);
		cell.setCellValue("Component ID");
		cell = row.createCell(3);
		cell.setCellType(CellType.STRING);
		cell.setCellValue("Key");
		cell = row.createCell(4);
		cell.setCellType(CellType.STRING);
		cell.setCellValue("Value");
		cell = row.createCell(5);
		cell.setCellType(CellType.STRING);
		cell.setCellValue("MEMO_SQL");
		// cell = row.createCell(6);
		// cell.setCellType(CellType.STRING);
		// cell.setCellValue("Path");
		/* Row 0 - Header END */

		// Iterate from Row 1 onwards
		int rowCount = 1;

		for (Item item : itemList) {
		    if (null != item.getArgumentMap() && !item.getArgumentMap().isEmpty()) {

			String refProcessId = "";
			for (Map.Entry<String, String> entry : item.getArgumentMap().entrySet()) {

			    row = sheet.createRow(rowCount);

			    cell = row.createCell(0);
			    cell.setCellType(CellType.NUMERIC);
			    cell.setCellValue(rowCount);

			    cell = row.createCell(1);
			    cell.setCellType(CellType.STRING);
			    cell.setCellValue(CustomUtility.sanitize(item.getSfcId()));

			    cell = row.createCell(2);
			    cell.setCellType(CellType.STRING);
			    cell.setCellValue(CustomUtility.sanitize(item.getProcessId()));

			    cell = row.createCell(3);
			    cell.setCellType(CellType.STRING);
			    cell.setCellValue(CustomUtility.sanitize(entry.getKey()));

			    cell = row.createCell(4);
			    cell.setCellType(CellType.STRING);
			    cell.setCellValue(CustomUtility.sanitize(entry.getValue()));

			    if (!refProcessId.equals(item.getProcessId())) {
				refProcessId = item.getProcessId();
				cell = row.createCell(5);
				cell.setCellType(CellType.STRING);
				cell.setCellValue(item.getMemoSql());
			    }

			    // !IMPT
			    rowCount++;
			    row = null;
			    cell = null;
			}

		    } else {
			System.out.println("!!! No args map for " + item.getPath());
		    }

		}

		fileOut = new FileOutputStream(excelFile);
		workbook.write(fileOut);
	    } catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    } finally {
		workbook.close();
		if (null != fileOut) {
		    fileOut.close();
		}
	    }
	} else {
	    System.out.println("!!!ItemList is empty!");
	}
    }

    public static Map<String, String> extractArguments(String expr, String path) {

	Map<String, String> returnMap = null;
	Matcher matcher = PATTERN_ARGS.matcher(expr);

	if (matcher.matches()) {
	    String argStr = matcher.group(1);

	    String[] args = argStr.replaceAll("\"", "").split("\\,");

	    try {
		if (null != argStr && !"".equals(argStr) && args.length > 0) {
		    returnMap = new HashMap<String, String>();
		    returnMap.put(Constants.KEY_PROC_ID, sanitize(args[0]));
		    returnMap.put(Constants.KEY_SFC_ID, sanitize(getSfcId(path)));
		    returnMap.put(Constants.KEY_OP_DATE, parse(args[1]));
		    for (int x = 2; x < args.length;) {
			returnMap.put(sanitize(args[x]), parse(args[x + 1]));
			x += 2;
		    }
		}
	    } catch (Exception e) {
		// handle NPE, index out of bounds
	    }
	    // System.out.println(returnMap);
	} else {
	    System.out.println("!!!ParamStr does not match regular expression!");
	}
	return returnMap;
    }

    public static String getSfcId(String str) {
	String returnStr = "";
	if (null != str) {
	    returnStr = str.substring(0, str.indexOf("_"));
	}
	return returnStr;
    }

    public static String sanitize(String str) {
	String returnStr = "";
	if (null != str) {
	    returnStr = str.trim();
	}
	return returnStr;
    }

    public static String parse(String str) {
	String returnstr = "";

	if (null != str && "" != str) {
	    returnstr = CustomUtility.sanitize(str);
	    
	    if (returnstr.contains("LOT_NUM")) {
		returnstr = "(LOT_NUM)";
	    }

	     if (returnstr.contains("(") && returnstr.contains(")")) {

	    returnstr = returnstr.substring(returnstr.indexOf("(") + 1, returnstr.length() - 1);

	     }

	    if (returnstr.contains("context.")) {
		returnstr = returnstr.replaceAll("context.", "");
	    }
	}

	return returnstr;
    }

    @Deprecated
    public static void copyFilestoNewdirectory(String source, String destfolder) {

	List<File> list = CustomUtility.getAllPaths(source);

	for (File item : list) {
	    // CustomUtility.parse(item.getAbsolutePath());
	    // System.out.println(item.getAbsolutePath());
	    File dest = new File(destfolder + item.getName().replaceAll(".item", ".xml"));
	    try {
		FileUtils.copyFile(item, dest);
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}

    }

}
