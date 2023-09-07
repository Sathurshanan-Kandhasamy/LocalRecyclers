import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Class for managing file read and write.
 */
public class FileManager
{
    // Stores the name of the file to read from and write to.
    String fileName = "LocalRecyclers.csv";

    /**
     * Method for creating, updating, and deleting a recycler in LocalRecyclers.csv file.
     * @param data An array of Recycler objects.
     */
    public void WriteDataToFile(Recycler[] data)
    {
        //All read/write operations in Java need to be contained within a try/catch structure because they
        //are interacting with resources outside the application files. This is to handle errors if
        //connection issues occur.
        try
        {
            //Creates a buffered writer, which is the class that streams the desired data to the file.
            //This writer writes the data incrementally, so it won't freeze your application if you are trying to
            //write data faster than the class can output it.
            BufferedWriter buffer = new BufferedWriter(new FileWriter(fileName));
            //Cycle through the array of data provided to the method.
            for (int i = 0; i < data.length; i++) {
                //Check if the current index in the data is null or not. If it is null, break the loop.
                if (data[i] == null) {
                    break;
                }
                //Write each entry in a delimited format before starting a new line.
                buffer.write(data[i].toString());
                buffer.newLine();
            }
            //Closes the buffer which causes it to flush out any remaining data and end the connection to the file.
            buffer.close();
        }
        catch(Exception ex)
        {
            //Print error message to console if an exception occurs
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Method for reading recyclers information from LocalRecyclers.csv file.
     * @return An array of Recycler objects or null.
     */
    public Recycler[] ReadDataFromFile()
    {
        //All read/write operations in Java need to be contained within a try/catch structure because they
        //are interacting with resources outside the application files. This is to handle errors if
        //connection issues occur.
        try
        {
            //Creates a buffered reader, which is the class that streams the desired data from the file.
            //This reader reads the data incrementally, so it won't freeze your application if you are trying to
            //read data faster than the application can process it.
            BufferedReader buffer = new BufferedReader(new FileReader(fileName));

            //Create a new empty array to hold our records when they are read into the app.
            Recycler[] data = new Recycler[100];
            //A counter to track how many entries we have currently entered. This will be used to know
            //which array index to add the next record to when each one is read in.
            int counter = 0;
            //String variable to hold each line as it is read in before we process it.
            String line;

            //Inside the while loop, read the next line from the file. If it is not null or empty
            //process the while loop code.
            while((line = buffer.readLine()) != null)
            {
                //Take the recently read line and split it using the semicolons as the delimiter
                String[] temp = line.split(";");
                for (int i = 1; i < temp.length; i++) {
                    System.out.println(temp[i]);
                }
                //At the index indicated by the counter value, create a new birthday entry and pass the split sections of
                //the line into the constructor to pre-populate the entry
                data[counter] = new Recycler(temp[0],temp[1],temp[2], temp[3], temp[4]);
                //Increase the counter by 1
                counter++;
            }
            //Closes the buffer which causes it to flush out any remaining data and end the connection to the file.
            buffer.close();
            //Return the finalised array back to where it was requested.
            return data;
        }
        catch(Exception ex)
        {
            //Print error message to console if an exception occurs
            System.out.println(ex.getMessage());
            //If an error occurred, return null to indicate an issue.
            return null;
        }
    }
}
