import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;

/**
 * Main class which inherits(extends) from the JFrame class. By doing this MainForm class gets access to all the pre-written
 * code and functionality of the JFrame class (such as adding components and drawing itself on screen) without the need to rewrite the
 * code that achieves this. This class is just considered as already having the code that is in the JFrame class as part of its own
 * code and logic.
 */
public class MainForm extends JFrame implements ActionListener
{

    // Create a new file manager class for reading and writing to the data file.
    FileManager file = new FileManager();

    // Creating the class object we will use for laying out components on screen.
    SpringLayout layout = new SpringLayout();

    /**
     * Declare the components that we will be using in the form. Each one is named using a prefix to help
     * identify its type when you see it later in the code.
     */
    JLabel lblHeader;
    JLabel lblBusinessName,lblAddress,lblPhone, lblWebsite, lblRecycles;
    JTextField txtBusinessName,txtAddress,txtPhone, txtWebsite, txtRecycles;
    JButton btnNew,btnSave,btnDelete;
    JButton btnFirst, btnPrev, btnNext, btnLast;
    JButton btnSort,btnBinary,btnFilter;
    JTextField txtFilter;
    JScrollPane scrollPane;
    JTextArea txtOutput;
    JButton btnFind;
    JTextField txtFind;
    JButton btnExit;

    // Declare an array which can store up to a hundred Recycler objects.
    Recycler[] recyclers = new Recycler[100];
    // Keeps track of how many entries are currently filled out in the array.
    int numberOfRecyclers = 0;
    // Tracks the current index in the array that we are viewing/interacting with.
    int CurrentRecycler = 0;
    /**
     *  Tracks whether the next time the save button is pressed, whether it saves the object as a new entry
     *  or an edited entry.
     */
    boolean isNewEntry = true;

    // MainForm constructor.
    public MainForm()
    {
        // Triggers the set size command that is inherited from JFrame to set the size values of the form.
        setSize(720,520);
        /**
         * Triggers the set location command that is inherited from JFrame to set the starting location values of the form.
         * These are calculated from the top left corner of the form
         */
        setLocation(400,200);
        // Telling the form to use the layout system we give it when positioning components.
        setLayout(layout);
        /**
         * Gets the content pane component(The part that holds the content and style settings) of the JFrame and
         * sets its background value.
         */
        getContentPane().setBackground(Color.WHITE);

        /**
         * Adds a window listener object to the Frame to respond to window events such as the window opening, closing etc.
         * The Window adapter that is passed into this method is a separate class object which holds the logic to be
         * executed when the triggered event occurs. In this example the event being setup is the window closing event
         * which runs the code to shut down the application.
         */
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        // Invokes method that renders header part of the GUI.
        BuildHeader();
        // Invokes method that renders form fields and find (by business name) field/button.
        BuildForm();
        // Invokes method that renders new, save, and delete buttons.
        BuildNewSaveDeleteButtons();
        // Invokes method that renders navigation buttons such as first, last, previous, and next.
        BuildNavigationButtons();
        // Invokes method that renders sort, binary search, and filter buttons/input field.
        BuildSortAndSearchComponents();
        // Invokes method that renders textarea and exit button.
        BuildOutputWindowSection();

        // Reads the data from the file and stores it in the recyclers array.
        recyclers = file.ReadDataFromFile();
        // Checks how many entries wer in the recently inputted file.
        UpdateNumberOfRecyclers();

        // Checks if there is currently any entries in the array.
        if (numberOfRecyclers > 0)
        {
            // If so, set the current entry value to the last index.
            CurrentRecycler = numberOfRecyclers - 1;
            /**
             * Show the current entry on screen, we only want this to run from inside this if statement, or
             * it will potentially crash.
             */
            displayCurrentRecycler();
            // Set the save type to edit if there is data already.
            isNewEntry = false;
        }

        /**
         * Calls the method from the JFrame class that turns the frame on and makes it draw onto the screen.
         * This method needs to be done after all UI setup is completed otherwise odd errors can occur such as
         * missing components of components drawing in the wrong place etc.
         */
        setVisible(true);
    }

    // Method for rendering header section of the GUI.
    private void BuildHeader() {
        //Uses the UIBuilder library to build a label based upon the top left corner.
        lblHeader = UIBuilderLibrary.BuildJLabelWithNorthWestAnchor("Local Recyclers",0,0,layout,this);
        /**
         * Sets the preferred size for the button. This will conform to this specified size unless something
         * requires it to modify in which case it will resize slightly to meet any anchoring/positioning requirements.
         */
        lblHeader.setPreferredSize(new Dimension(708,30));
        //Sets the text to be centred in the label
        lblHeader.setHorizontalAlignment(JLabel.CENTER);
        //Changes the font of the label. This requires defining a new font object which takes a font family name,
        //a style(bold,italic,etc) and size
        lblHeader.setFont(new Font("Arial", Font.BOLD,25));
        //Sets the background of the label to opaque, so it's colour will show. This is because label backgrounds
        //are set to transparent by default.
        lblHeader.setOpaque(true);
        //Sets the background colour of the label.
        lblHeader.setBackground(new Color(0,120,0,155));
        //Adds the label to the frame
        add(lblHeader);
    }

    // Method for rendering form fields and find (by business name) field/button.
    private void BuildForm() {
        //Uses the UIBuilder library to build a label based upon the top left corner then add it to the frame
        lblBusinessName = UIBuilderLibrary.BuildJLabelWithNorthWestAnchor("Business Name:",20,50,layout,this);
        add(lblBusinessName);
        //Uses the UIBuilder library to build a label directly below the specified component then add it to the frame
        lblAddress = UIBuilderLibrary.BuildJLabelInlineBelow("Address:", 10,layout,lblBusinessName);
        add(lblAddress);
        //Uses the UIBuilder library to build a label directly below the specified component then add it to the frame
        lblPhone = UIBuilderLibrary.BuildJLabelInlineBelow("Phone:",10,layout,lblAddress);
        add(lblPhone);
        lblWebsite = UIBuilderLibrary.BuildJLabelInlineBelow("Website:",10,layout,lblPhone);
        add(lblWebsite);
        lblRecycles = UIBuilderLibrary.BuildJLabelInlineBelow("Recycles:",10,layout,lblWebsite);
        add(lblRecycles);
        //Uses the UIBuilder library to build a text-field directly to the right of the specified component then add it to the frame.
        txtBusinessName = UIBuilderLibrary.BuildJTextFieldInlineToRight(20,35,layout,lblBusinessName);
        add(txtBusinessName);
        txtAddress = UIBuilderLibrary.BuildJTextFieldInlineBelow(20,5,layout,txtBusinessName);
        add(txtAddress);
        txtPhone = UIBuilderLibrary.BuildJTextFieldInlineBelow(20,5,layout,txtAddress);
        add(txtPhone);
        txtWebsite = UIBuilderLibrary.BuildJTextFieldInlineBelow(20,5,layout,txtPhone);
        add(txtWebsite);
        txtRecycles = UIBuilderLibrary.BuildJTextFieldInlineBelow(20,5,layout,txtWebsite);
        add(txtRecycles);
        //Positions the Find Button and search field below the data input fields of the form.
        btnFind = UIBuilderLibrary.BuildJButtonInlineBelow(173,25,"Find by Business Name:", 10,this,layout,lblRecycles);
        add(btnFind);
        txtFind = UIBuilderLibrary.BuildJTextFieldInlineToRight(20,5,layout,btnFind);
        add(txtFind);
    }

    // Method for rendering build new, save, and delete buttons.
    private void BuildNewSaveDeleteButtons() {
        //Set up our buttons on a group with the first button anchored based upon the form and the other buttons each anchored below each other.
        btnNew = UIBuilderLibrary.BuildJButtonWithNorthWestAnchor(85,25,"New",500,50,this,layout,this);
        add(btnNew);
        btnSave = UIBuilderLibrary.BuildJButtonInlineBelow(85,25,"Save",5,this,layout,btnNew);
        add(btnSave);
        btnDelete = UIBuilderLibrary.BuildJButtonInlineBelow(85,25,"Delete",5,this,layout,btnSave);
        add(btnDelete);
    }

    // Method for rendering navigation buttons.
    private void BuildNavigationButtons() {
        //Set up our navigation buttons in a group with each button except the first anchored to the right of the previous button.
        btnFirst = UIBuilderLibrary.BuildJButtonWithNorthWestAnchor(35,25,"|<",500,180,this,layout,this);
        btnFirst.setMargin(new Insets(0,0,0,0));
        add(btnFirst);

        btnPrev = UIBuilderLibrary.BuildJButtonInlineToRight(35,25,"<<",0,this,layout,btnFirst);
        btnPrev.setMargin(new Insets(0,0,0,0));
        add(btnPrev);

        btnNext = UIBuilderLibrary.BuildJButtonInlineToRight(35,25,">>",0,this,layout,btnPrev);
        btnNext.setMargin(new Insets(0,0,0,0));
        add(btnNext);

        btnLast = UIBuilderLibrary.BuildJButtonInlineToRight(35,25,">|",0,this,layout,btnNext);
        btnLast.setMargin(new Insets(0,0,0,0));
        add(btnLast);
    }

    // Method for rendering sort, binary search, and filter components.
    private void BuildSortAndSearchComponents() {
        btnSort = UIBuilderLibrary.BuildJButtonWithNorthWestAnchor(170,25,"Sort by Business Name",20,220,this,layout,this);
        add(btnSort);

        btnBinary = UIBuilderLibrary.BuildJButtonInlineToRight(225,25,"Binary Search by Business Name",5,this,layout,btnSort);
        add(btnBinary);

        btnFilter = UIBuilderLibrary.BuildJButtonInlineToRight(182,25,"Filter by Recycle Product:",5,this,layout,btnBinary);
        add(btnFilter);

        txtFilter = UIBuilderLibrary.BuildJTextFieldInlineToRight(9,5,layout,btnFilter);
        add(txtFilter);
    }

    // Method for rendering output window section.
    private void BuildOutputWindowSection() {
        //Create a new text area, but don't define it's size. It will be sized and positioned according to the scroll pane once added to it.
        txtOutput = new JTextArea();
        //Turn wordwrap on for the text area and set it to wrap the full word if possible
        txtOutput.setLineWrap(true);
        txtOutput.setWrapStyleWord(true);

        //Create a scroll pane (panel with scrollbars) and add the text area to it.
        scrollPane = new JScrollPane(txtOutput);
        //Set the scroll pane's size and position.
        layout.putConstraint(SpringLayout.NORTH,scrollPane,10,SpringLayout.SOUTH,btnSort);
        layout.putConstraint(SpringLayout.WEST,scrollPane,0,SpringLayout.WEST,btnSort);
        scrollPane.setPreferredSize(new Dimension(680,180));
        add(scrollPane);

        //Add the Exit button and line it up with the bottom right edge of the scroll pane.
        btnExit = new JButton("Exit");
        btnExit.addActionListener(this);
        btnExit.setPreferredSize(new Dimension(80,25));
        layout.putConstraint(SpringLayout.NORTH,btnExit,10,SpringLayout.SOUTH,scrollPane);
        layout.putConstraint(SpringLayout.EAST,btnExit,0,SpringLayout.EAST,scrollPane);
        add(btnExit);
    }

    // Update number of recyclers
    private void UpdateNumberOfRecyclers()
    {
        //Check if the recyclers array is null. If not, run the loop inside the if statement.
        if(recyclers != null)
        {
            //Cycle through the array of birthday records
            for (int i = 0; i < recyclers.length; i++)
            {
                //If you find a record that is empty
                if(recyclers[i] == null)
                {
                    //Set the number of entries to be the number of whichever loop you are up to
                    numberOfRecyclers = i;
                    //Break out of the loop.
                    break;
                }
            }
        }
        //If the birthday array was null, likely due to a file read error
        else
        {
            //Create a new empty birthday array
            recyclers = new Recycler[100];
            //Set the number of entries to zero.
            numberOfRecyclers = 0;
        }
    }

    // Displays the array element matching the index of the current entry variable on screen.
    private void displayCurrentRecycler()
    {
        //If this method was called when there is no data
        if(numberOfRecyclers == 0)
        {
            //Clear the data fields, in case something was typed there
            ClearEntryFields();
            //Set is new entry to true so that if save is pressed it won;t try to update non-existent entries.
            isNewEntry = true;
            //End the method
            return;
        }
        //Otherwise, copy the details form the current entry index of the array to the form fields
        txtBusinessName.setText(recyclers[CurrentRecycler].getBusinessName());
        txtAddress.setText(recyclers[CurrentRecycler].getAddress());
        txtPhone.setText(recyclers[CurrentRecycler].getPhone());
        txtWebsite.setText(recyclers[CurrentRecycler].getWebsite());
        txtRecycles.setText(recyclers[CurrentRecycler].getRecycles());
    }

    // Clears all the form input fields.
    private void ClearEntryFields()
    {
        txtBusinessName.setText("");
        txtAddress.setText("");
        txtPhone.setText("");
        txtWebsite.setText("");
        txtRecycles.setText("");
    }

    //Method triggered by action events(button presses etc. ) when the form is notified of the event.
    @Override
    public void actionPerformed(ActionEvent e)
    {
        //Checks which component triggered the event by getting the source value from the ActionEvent data.
        //If the source matches the specified component in any of the if statements below, that statement runs.

        // If new and save buttons are not clicked.
        if (e.getSource() != btnNew && e.getSource() != btnSave)
        {
            // set isNewEntry to false.
            isNewEntry = false;
        }

        // If new button clicked.
        if (e.getSource() == btnNew)
        {
            // Clear form input fields and set isNewEntry to true.
            ClearEntryFields();
            isNewEntry = true;
        }

        // If save button is clicked.
        if (e.getSource() == btnSave)
        {
            // Invoke SaveRecycler method.
            SaveRecycler();
        }

        // If delete button is clicked.
        if(e.getSource() == btnDelete)
        {
            //If there are no entries in the array. Return out of the method.
            if (numberOfRecyclers == 0)
            {
                return;
            }
            DeleteRecycler();
        }

        // If first button is clicked.
        if (e.getSource() == btnFirst)
        {
            //Set the CurrentRecycler to the first array index and then display it on screen.
            CurrentRecycler = 0;
            displayCurrentRecycler();
        }
        // If previous button is clicked.
        if (e.getSource() == btnPrev)
        {
            //Check that we are not already on the first index
            if (CurrentRecycler > 0)
            {
                //Decrease the index by one and display the current index entry
                CurrentRecycler--;
                displayCurrentRecycler();
            }
        }
        // If next button is clicked.
        if (e.getSource() == btnNext)
        {
            //Check that we are not already on the last index with data
            if (CurrentRecycler < numberOfRecyclers -1)
            {
                //Increase the index by one and display the current index entry
                CurrentRecycler++;
                displayCurrentRecycler();
            }
        }
        // If last button is clicked.
        if (e.getSource() == btnLast)
        {
            //Set the CurrentRecycler to the last array index with data and then display it on screen
            CurrentRecycler = numberOfRecyclers -1;
            displayCurrentRecycler();
        }

        // If sort and binary search buttons are clicked.
        if (e.getSource() == btnSort || e.getSource() == btnBinary)
        {
            SortAndBinarySearchEntries(e);
        }

        // If filter button is clicked.
        if(e.getSource() == btnFilter)
        {
            //Set the text of the text area to a default starting line
            txtOutput.setText("Entries matching search filter:");
            //Cycle through all the birthday entries with data
            for (int i = 0; i < numberOfRecyclers; i++)
            {
                //If the current element's ideas field contains the keyword given in the filter text field
                //Both strings being compared need to have the same casing to match, which is why both were converted to lower case as part
                //of the check.
                if (recyclers[i].getRecycles().toLowerCase().contains(txtFilter.getText().toLowerCase()))
                {
                    //Print the matching entry to the text field.
                    txtOutput.append("\n" + recyclers[i].toString());
                }
            }
        }

        // If find button is clicked.
        if (e.getSource() == btnFind)
        {
            //Cycle through all the birthday entries with data
            for (int i = 0; i < numberOfRecyclers; i++)
            {
                //If the current element's ideas field contains the keyword given in the filter text field
                //Both strings being compared need to have the same casing to match, which is why both were converted to lower case as part
                //of the check.
                if (recyclers[i].getBusinessName().toLowerCase().contains(txtFind.getText().toLowerCase()))
                {
                    //Set the current entry to the index we were up to and display it before breaking the loop.
                    CurrentRecycler = i;
                    displayCurrentRecycler();
                    break;
                }
            }
        }

        // If exit button is clicked.
        if(e.getSource() == btnExit)
        {
            System.exit(0);
        }
    }

    // Method for performing binary search.
    private void SortAndBinarySearchEntries(ActionEvent e) {
        //Create an empty array that has a size equal to the number of entries in the birthday data array
        Recycler[] sortedArray = new Recycler[numberOfRecyclers];
        //Copy the entries from the main array to the new array. The number after each array name indicates which index
        //to start reading to/writing from during the copy. The last value (number of entries) tells the method how may elements to copy across
        //from these starting positions.
        System.arraycopy(recyclers,0, sortedArray,0, numberOfRecyclers);
        //Sort the provided array in ascending order.
        Arrays.sort(sortedArray);

        //Clears the text area and then write the first line of our output
        txtOutput.setText(sortedArray[0].toString());

        for (int i = 1; i < sortedArray.length; i++)
        {
            //Append (add to) the current text of the text area.
            txtOutput.append("\n" + sortedArray[i].toString());
        }

        if (e.getSource() == btnBinary)
        {
            //Run the binary search method on the sorted array to find the entry matching the provided name in the filter search box.
            //This method will return the element index number of where it was found in the array, or a negative value if it was not found.
            int index = Arrays.binarySearch(sortedArray, txtFilter.getText());
            //Checks if the search term was found or not and prints the relevant message.
            if (index < 0)
            {
                txtOutput.append("\n\n" + txtFilter.getText() + " was not found.");
            }
            else
            {
                txtOutput.append("\n\n" + txtFilter.getText() + " was found at index: " + index);
            }
        }
    }

    // Method for deleting an existing recycler.
    private void DeleteRecycler() {
        //Cycle through the data array starting at the current entry(the one being removed), until the last entry with data.
        for (int i = CurrentRecycler; i < numberOfRecyclers; i++)
        {
            //If the current index is the last entry with data
            if (i == numberOfRecyclers -1)
            {
                //Set the index to null and break the loop.
                recyclers[i] = null;

                //If the entry we are deleting is the last one in the array.
                if (CurrentRecycler == numberOfRecyclers -1)
                {
                    //Because we are deleting the last entry, reduce set the current
                    CurrentRecycler--;
                }
                break;
            }
            //Copy the contents of the next index in the array into the current index's position.
            recyclers[i] = recyclers[i +1];
        }
        //Decrease the number of entries to reflect that an entry has been removed
        numberOfRecyclers--;
        //Display the new current entry and save the changes to the file.
        displayCurrentRecycler();
        file.WriteDataToFile(recyclers);
    }

    // Method for creating a new recycler.
    private void SaveRecycler() {
        //Create a new empty birthday data object
        Recycler data = new Recycler();
        //For each property of the object, call the associated setter method and pass it the text content of the
        //related text field of the form.
        data.setBusinessName(txtBusinessName.getText());
        data.setAddress(txtAddress.getText());
        data.setPhone(txtPhone.getText());
        data.setWebsite(txtWebsite.getText());
        data.setRecycles(txtRecycles.getText());

        if (isNewEntry)
        {
            //Go to the next empty index of the recyclers array. This empty index will share the same value as the
            //numberOfRecyclers counter, so we can use it to specify the correct index.
            recyclers[numberOfRecyclers] = data;
            //Set the CurrentRecycler value to the number of entries value. This will let our application know that this is the
            //number current index being shown on screen.
            CurrentRecycler = numberOfRecyclers;
            //Increase the number of entries by one to indicate a new value has been entered and therefore this value is now higher.
            numberOfRecyclers++;
            //Show message box to confirm completion of save
            JOptionPane.showMessageDialog(this, "New Entry Saved Successfully.");
        }
        else
        {
            //Save the data over the existing data at the current index element.
            recyclers[CurrentRecycler] = data;
            //Show message box to confirm completion of update
            JOptionPane.showMessageDialog(this, "Selected Entry Updated.");
        }
        isNewEntry = false;

        //Pass the birthday data to the file manager to be written to the .csv file.
        file.WriteDataToFile(recyclers);
    }
}
