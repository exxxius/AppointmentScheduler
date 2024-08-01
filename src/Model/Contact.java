package Model;

/**
 * This class creates the contact objects.
 *
 * @author Mehdi Rahimi
 */
public class Contact {
    /**
     * The contact ID.
     */
    private int contactId;
    /**
     * The contact name.
     */
    private String contactName;
    /**
     * The contact email.
     */
    private String email;

    /**
     * This is the constructor for the Contact class.
     *
     * @param contactId   The contact ID.
     * @param contactName The contact name.
     * @param email       The contact email.
     */
    public Contact(int contactId, String contactName, String email) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.email = email;
    }

    /**
     * This function returns the contactId of the contact.
     *
     * @return The contactId
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * This function sets the contactId of the contact.
     *
     * @param contactId The contactId to set
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * This function returns the contactName of the contact.
     *
     * @return The contactName
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * This function sets the contactName of the contact.
     *
     * @param contactName The contactName to set
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * This function returns the email of the contact.
     *
     * @return The email
     */
    public String getEmail() {
        return email;
    }

    /**
     * This function sets the email of the contact.
     *
     * @param email The email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * This function returns the contactName of the contact.
     *
     * @return The contactName
     */
    @Override
    public String toString() {
        return contactName;
    }
}