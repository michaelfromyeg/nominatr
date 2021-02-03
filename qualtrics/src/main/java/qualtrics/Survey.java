package qualtrics;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Survey {
 
  public Survey() {}
  
  // EFFECTS: Unzip the resulting file extracted from Qualtrics
  public void unzip(String fileName) throws Exception {
      String source = fileName; 
      String destination = Paths.get("qualtrics", "out").toAbsolutePath().toString();   
      try {
          ZipFile zipFile = new ZipFile(source);
          zipFile.extractAll(destination);
      } catch (ZipException e) {
          e.printStackTrace();
          throw new Exception("Error unzipping file");
      }
  }

  public void normalizeFileName() {
    for(File file: new File("./qualtrics/out").listFiles()) {
        String extension = "";
        int i = file.getName().lastIndexOf('.');
        if (i > 0) {
            extension = file.getName().substring(i+1);
        }
        if (!file.isDirectory() && extension.length() > 0 && extension.equals("csv")) {
            file.renameTo(new File("./qualtrics/out/survey.csv"));
        }
    }
  }

  public List<Response> fetch(String fileName) throws Exception {
    try {
        List<Response> responses = new ArrayList<>();
        CSVReader reader = new CSVReader(new FileReader("./qualtrics/out/survey.csv"));
        List<String[]> r = reader.readAll();

        // Collect responses, beginning at the third row (skipping Qualtrics preamble)
        for (int i = 3; i < r.size(); i++) {
            responses.add(new Response(r.get(i)[0], r.get(i)[1], r.get(i)[2], r.get(i)[3], r.get(i)[4], r.get(i)[5], r.get(i)[6],
                r.get(i)[7], r.get(i)[8], r.get(i)[9], r.get(i)[10], r.get(i)[11], r.get(i)[12], r.get(i)[13], r.get(i)[14], r.get(i)[15], r.get(i)[16],
                r.get(i)[17], r.get(i)[18], r.get(i)[19], r.get(i)[20], r.get(i)[21], r.get(i)[22], r.get(i)[23], r.get(i)[24], r.get(i)[25], r.get(i)[26],
                r.get(i)[27], r.get(i)[28], r.get(i)[29], r.get(i)[30]));
        }
        
        return responses;
    } catch (Exception e) {
        e.printStackTrace();
        throw new Error("Error reading CSV and creating response list");
    }
  }
}
