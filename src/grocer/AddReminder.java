package grocer;

import javax.swing.JOptionPane;


/**
 * Lets the user write a new reminder for a specific date
 */
public class AddReminder {

  public static void writeReminder() {
    String date;
    date = JOptionPane.showInputDialog("Date (yyyy-mm-dd)");
    String message;
    message = JOptionPane.showInputDialog("Reminder");
    String reminder;
    reminder = "You will be reminded on " + date + " to " + message;
    JOptionPane.showMessageDialog(null, reminder);
    WriteNoteFile newNote = new WriteNoteFile(message, date);
    newNote.write();

  }



}