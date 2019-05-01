package grocer;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

/*
This class is used by pop up to write their reminder into a new file
 */
public class WriteNoteFile {

  public String message;
  public String date;

  public WriteNoteFile(String message, String date) {
    this.message = message;
    this.date = date;

  }

  public void write() {
    Writer writer = null;
    try {
      writer = new BufferedWriter(new OutputStreamWriter(
              new FileOutputStream("src/grocer/Reminders/" + date + ".txt"), "utf-8"));//2017-08-05

      writer.write(message);
    } catch (IOException ex) {
      // report
    } finally {
      try {
        writer.close();
      } catch (Exception ex) {/*ignore*/}
    }

  }
}