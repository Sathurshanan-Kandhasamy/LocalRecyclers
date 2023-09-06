/**
 * Data Model to represent a single set of Birthday data about an individual person. Each time we need to create a set of details about a
 * person we will create on of these objects and populate it with data.
 */
public class Recycler implements Comparable
{
    //Variables to store the properties associated with each set of data. These variables are all private which means they can only be
    //accessed or changed from within the class itself. To access these values externally you need to use one of the getters or setters
    //provided further below to request access.
    String businessName;
    String address;
    String phone;
    String website;
    String recycles;

    //A blank constructor which allows us to create an instance of this object which is blank and has no
    //pre-filled values.
    //IMPLEMENTATION EXAMPLE: BirthdayData myData = new BirthdayData();
    public Recycler()
    {

    }

    //Constructor variation that takes 3 parameters upon creation. These parameters are then passed to the variables in the
    //object to pre-fill its details upon creation. This variation will only work if 3 string variables are provided.
    //IMPLEMENTATION EXAMPLE: BirthdayData myData = new BirthdayData("Troy","12/12/2004", "Beer, Games");
    public Recycler(String businessName, String address, String phone, String website, String recycles)
    {
        this.businessName = businessName;
        this.address = address;
        this.phone = phone;
        this.website = website;
        this.recycles = recycles;
    }

    //The following methods are the getters and setters that must be used to access the private variables of the class. The methods
    //beginning with set each take a value and assign it ot the associated variable. The ones beginning with get retrieve the value of the
    // variables and return them to where the methods are called from.
    //This system is more secure than simply setting each of the variables as public which would make both reading and writing to each variable
    //public to all areas of the application. By using getters and setters instead we can have different access rules if needed for read and write
    //access by setting one method type to public and the other to private or by removing it entirely.
    // For example, we can set all the getters to public and then make all the setters private. This would mean anyone could read the variable but
    //no-one outside this class can set or change the variable values. We can also use these methods to add additional checks to the processes such as
    //running validation or permission checks before trying to change any values.
    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getRecycles() {
        return recycles;
    }

    public void setRecycles(String recycles) {
        this.recycles = recycles;
    }

    @Override
    public String toString()
    {
        return businessName + ";" + address + ";" + phone + ";" + website + ";" + recycles;
    }

    //Overrides the default comparison method for this object so that we can define how the comparison is performed.
    //This is done by adding the Comparable interface to this class (see above).
    @Override
    public int compareTo(Object other)
    {
        //Checks whether the object provided in our parameter was a string type
        if (other instanceof String)
        {
            //Converts the object into a string type and stores it as a variable
            String otherString = (String)other;
            //Tells the method to return the result of running the standard string comparison using the name fields of this object as the
            //strings being compared and the provided string as the other value.
            return businessName.compareToIgnoreCase(otherString);
        }

        Recycler otherData = (Recycler)other;
        //Tells the method to return the result of running the standard string comparison using the name fields of each object as the
        //strings being compared.
        return businessName.compareToIgnoreCase(otherData.getBusinessName());
    }
}

