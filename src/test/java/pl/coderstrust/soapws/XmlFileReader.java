package pl.coderstrust.soapws;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class XmlFileReader {

  public String readFromFile(String filePath) throws FileNotFoundException {
    List<String> listOfLinesFromFile = new ArrayList<>();
    try (Scanner scan = new Scanner(new File(filePath))) {
      while (scan.hasNextLine()) {
        listOfLinesFromFile.add(scan.nextLine());
      }
      StringBuffer stringVar = new StringBuffer();
      for (String line : listOfLinesFromFile) {
        stringVar.append(line.trim());
      }
      return stringVar.toString();
    }
  }
}

