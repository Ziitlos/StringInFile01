package ch.hannes.app;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ListFilesUtil {

	// 1. Liste mit allen Filenamen erstellen 
	/**
	 * Gibt alle Filenamen aus dem gewuenschten Verzeichnis als Liste zur�ck
	 * 
	 * @param directoryName
	 * @return List with Filenames
	 */
	public List<String> listFiles(String directoryName) {
		File directory = new File(directoryName);

		List<String> fileList = new ArrayList<String>();

		// get all the files from directory
		File[] fList = directory.listFiles();

		// Liste zusammenstellen der Filenamen aus Verzeichnis
		for (File file : fList) {
			if (file.isFile()) {
				fileList.add(file.getName());
			}
		}
		return fileList;
	}

	// 2. Liste mit allen QueryKey erstellen
	/**
	 * 
	 * Liest die Query Keys aus der ersten Splate im Excel File (*.xlsx)
	 * 
	 * @param directoryName
	 * @param excelFile
	 * @return List with QueryKeyNames
	 * @throws IOException
	 */
	public List<String> listQueryKey(String directoryName, String excelFile)throws IOException {
		// Liste mit den Query Keys
		List<String> queryKeyList = new ArrayList<String>();

		File myFile = new File(directoryName + "//" + excelFile);
		FileInputStream fis = new FileInputStream(myFile);

		// Finds the workbook instance for XLSX file XSSFWorkbook
		XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);

		// Return first sheet from the XLSX workbook XSSFSheet
		XSSFSheet mySheet = myWorkBook.getSheetAt(0);

		// Get iterator to all the rows in current sheet
		Iterator<Row> rowIterator = mySheet.iterator();

		// Gehe durch die Zeilen und lese erste Spalte
		while (rowIterator.hasNext()) {
			String actualQueryKey = rowIterator.next().getCell(0).toString();
			queryKeyList.add(actualQueryKey);
		}
		myWorkBook.close();
		return queryKeyList;
	}

	// 3a. Methode filesQueryKey(), return Wert muss eine Key Value Liste sein
	// Key = ein Filename
	// Value = Kommaseparierte Liste mit den gefundenen QueryKeys
	/**
	 * Erstellt eine Liste mit Filenamen und den gefundenen QueryKeys
	 * @param directoryName
	 * @param fileNames
	 * @param queryKeys
	 * @return resultMap
	 * @throws IOException
	 */
	public HashMap<String, String> filesQueryKey(String directoryName, List<String> fileNames, List<String> queryKeys) throws IOException {
		// HashMap f�r die gefundenen Resultate die dann zur�ck gegeben werden k�nnen
		HashMap<String, String> resultMap = new HashMap<String, String>();
		
		// Loop ueber alle im Verzeichnis gefundenen Files
		for (String fileToRead : fileNames) {
			// File einlesen und als String bereit stellen
			InputStream is = new FileInputStream(directoryName + "//" + fileToRead);
			BufferedReader buf = new BufferedReader(new InputStreamReader(is));
			String line = buf.readLine();
			StringBuilder sb = new StringBuilder();
			while (line != null) {
				sb.append(line).append("\n");
				line = buf.readLine();
			}
			String fileAsString = sb.toString();
			buf.close();
			
			//HashMap mit Filenamen als Key initialisieren
			resultMap.put(fileToRead, "");
			
			// Loop over Liste mit Querys, es wird im File nach jedem QueryKey gesucht
			for (String queryKey : queryKeys) {
				// Value der HashMap zwischenspeichern
				String tmpQueryKeys = resultMap.get(fileToRead);
				// Wenn ein QueryKey gefunden wird, so wird dieser dem HashMapValue angeh�ngt
				// Spitzige Klammern ">xxx<" sind notwendig, das eindeutig dieser String gefunden wird
				if(fileAsString.toLowerCase().contains(">" + queryKey.toLowerCase() + "<")){
					resultMap.put(fileToRead, tmpQueryKeys + ", " + queryKey);
				}
			}
		}
		return resultMap;
	}

	// 3b. Methode queryKeyFiles(), return Wert muss eine Key Value Liste sein
	// Key = ein Queryname
	// Value = Kommaseparierte Liste mit den Filenamen
	/**
	 * Erstellt eine Liste mit Query Keys und den gefundenen Filenamen
	 * 
	 * @param directoryName
	 * @param fileNames
	 * @param queryKeys
	 * @throws IOException
	 */
	public HashMap<String, String> queryKeyFiles(String directoryName, List<String> fileNames, List<String> queryKeys) throws IOException {
		// HashMap f�r die gefundenen Resultate die dann zur�ck gegeben werden k�nnen
		HashMap<String, String> resultMap = new HashMap<String, String>();
		
		// Loop �ber alle QueryKeys
		for(String queryKey : queryKeys){
			// System.out.println("---> queryKey: " + queryKey);
			
			//HashMap mit Filenamen als Key initialisieren
			resultMap.put(queryKey, "");
			
			// Loop �ber alle im Verzeichnis gefundenen Files
			for(String fileToRead : fileNames){
				// System.out.println("---> Filenamen: " + fileToRead);
				// File einlesen und als String bereit stellen
				InputStream is = new FileInputStream(directoryName + "//" + fileToRead);
				BufferedReader buf = new BufferedReader(new InputStreamReader(is));
				String line = buf.readLine();
				StringBuilder sb = new StringBuilder();
				while (line != null) {
					sb.append(line).append("\n");
					line = buf.readLine();
				}
				String fileAsString = sb.toString();
				buf.close();
				
				// Value der HashMap zwischenspeichern
				String tmpFileNames = resultMap.get(queryKey);
				// Wenn ein QueryKey gefunden wird, so wird der Filenamen dem HashMapValue angeh�ngt
				// Spitzige Klammern ">xxx<" sind notwendig, das eindeutig dieser String gefunden wird
				if(fileAsString.toLowerCase().contains(">" + queryKey.toLowerCase() + "<")){
					resultMap.put(queryKey, tmpFileNames + ", " + fileToRead);
				}
			}
		}
		return resultMap;
	}

	public static void main(String[] args) throws IOException {

		ListFilesUtil listFilesUtil = new ListFilesUtil();

		// Windows directory
		final String directoryWindows = "C://DEVLehrlinge//workspace//eclipse//FileList01//filesToRead";
		final String queryKeyXlsx = "export.xlsx";

		// 1. Liste mit allen Filenamen erstellen
		List<String> filesToRead = listFilesUtil.listFiles(directoryWindows);

		// Ausgabe der Liste mit allen Filenamen
		// System.out.println("Files, welche durchsucht werden sollen.");
		// for(String fileToRead : filesToRead){
		// System.out.println("A----> " + fileToRead);
		// }

		// 2. Liste mit allen QueryKey erstellen
		List<String> queryKeysToRead = listFilesUtil.listQueryKey(directoryWindows, queryKeyXlsx);

		// Ausgabe der Liste mit allen QueryKey
		// System.out.println("Query Keys, nach welchen gesucht werden soll.");
		// for(String queryKey : queryKeysToRead){
		// System.out.println("B----> " + queryKey);
		// }

		System.out.println("\n-------------------- Filenamen - Liste mit QueryKeys --------------------\n");
		
		// 3a. Files einzeln einlesen und in den Files nach den QueryKeys suchen und
		// in eine Liste aufnehmen.
		// Ausgabe: Filenamen - Liste mit QueryKeys
		HashMap<String, String> resultFileList = new HashMap<String, String>();
		resultFileList =  listFilesUtil.filesQueryKey(directoryWindows, filesToRead, queryKeysToRead);
		
		// Ausgabe der gesammelten Werte
		for(String key : resultFileList.keySet()){
			String value = resultFileList.get(key);
			System.out.println("File: " + key + " / QueryKeys: " + value);
		}
		
		System.out.println("\n\n-------------------- QueryKey - Liste mit Files in welchen er gefunden wurde. --------------------\n");
		
		// 3b. Files einzeln einlesen und in den Files nach den QueryKeys suchen und
		// in eine Liste aufnehmen.
		// Ausgabe: QueryKey - Liste mit Files in welchen er gefunden wurde.
		HashMap<String, String> resultQueryKeyList = new HashMap<String, String>();
		resultQueryKeyList = listFilesUtil.queryKeyFiles(directoryWindows, filesToRead, queryKeysToRead);
		
		// Ausgabe der gesammelten Werte
		for (String key : resultQueryKeyList.keySet()) {
			String value = resultQueryKeyList.get(key);
			System.out.println("QueryKey: " + key + " / Files: " + value);
		}
	}
// Ende
}
