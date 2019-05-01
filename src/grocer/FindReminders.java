package grocer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.ZoneId;
import javax.swing.JOptionPane;

/**
 * This class will find if there is a reminder for today, if there is, it will find it and read it
 */
public class FindReminders{

  public String date;



  public FindReminders(String date) {
    this.date = date;

  }

  public String fileExist() {
    ZoneId zonedId = ZoneId.of("America/Montreal");
    LocalDate today = LocalDate.now(zonedId);
    File folder = new File(System.getProperty("user.dir") + "/src/grocer/Reminders/");//find path to folder
    File[] listOfFiles = folder.listFiles();//list all files in folder
    for (int i = 0; i < listOfFiles.length; i++) {
      if (listOfFiles[i].isFile()) {
        if (listOfFiles[i].getName().equals(today.toString() + ".txt")) { //find file
          return "found";
        }
      }
    }
    return "not found";
  }

  public File getFile() {
    ZoneId zonedId = ZoneId.of("America/Montreal");
    LocalDate today = LocalDate.now(zonedId);
    return new File("src/grocer/Reminders/" + today.toString() + ".txt");

  }

  public String read(File file) throws Exception {

    BufferedReader br = new BufferedReader(new FileReader(file));
    try {
      StringBuilder sb = new StringBuilder();
      String line = br.readLine();

      while (line != null) {
        sb.append(line);
        sb.append("\n");
        line = br.readLine();
      }
      return sb.toString();
    } catch (Exception except){
      return null;
    }

    finally {
      br.close();
    }
  }


}