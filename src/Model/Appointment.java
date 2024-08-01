package Model;

import java.time.LocalDateTime;

/**
 * This class is used to create an appointment object that contains all the information about an appointment.
 *
 * @author Mehdi Rahimi
 */

public class Appointment {
    /**
     * The appointment ID.
     */
    private int appointmentId;
    /**
     * The title of the appointment.
     */
    private String title;
    /**
     * The description of the appointment.
     */
    private String description;
    /**
     * The location of the appointment.
     */
    private String location;
    /**
     * The type of the appointment.
     */
    private String type;
    /**
     * The start time of the appointment.
     */
    private LocalDateTime start;
    /**
     * The end time of the appointment.
     */
    private LocalDateTime end;
    /**
     * The creation date of the appointment.
     */
    private LocalDateTime createDate;
    /**
     * The user who created the appointment.
     */
    private String createdBy;
    /**
     * The last update time of the appointment.
     */
    private LocalDateTime lastUpdate;
    /**
     * The user who last updated the appointment.
     */
    private String lastUpdatedBy;
    /**
     * The customer ID associated with the appointment.
     */
    private int customerId;
    /**
     * The user ID associated with the appointment.
     */
    private int userId;
    /**
     * The contact ID associated with the appointment.
     */
    private int contactId;

    /**
     * This is the constructor for the Appointment class.
     *
     * @param appointmentId The appointment ID.
     * @param title The appointment title.
     * @param description The appointment description.
     * @param location The appointment location.
     * @param type The appointment type.
     * @param start The appointment start date and time.
     * @param end The appointment end date and time.
     * @param createDate The date the appointment was created.
     * @param createdBy The user who created the appointment.
     * @param lastUpdate The date the appointment was last updated.
     * @param lastUpdatedBy The user who last updated the appointment.
     * @param customerId The customer ID.
     * @param userId The user ID.
     * @param contactId The contact ID.
     */
    public Appointment(int appointmentId, String title, String description, String location, String type,
                       LocalDateTime start, LocalDateTime end, LocalDateTime createDate, String createdBy,
                       LocalDateTime lastUpdate, String lastUpdatedBy, int customerId, int userId, int contactId) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

    /**
     * @return the Appointment ID
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /**
     * @param appointmentID Sets the Appointment ID.
     */
    public void setAppointmentId(int appointmentID) {
        appointmentId = appointmentID;
    }

    /**
     * @return the Appointment title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param Title Sets the Appointment title.
     */
    public void setTitle(String Title) {
        title = Title;
    }

    /**
     * @return the Appointment description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param Description Sets the Appointment description.
     */
    public void setDescription(String Description) {
        description = Description;
    }

    /**
     * @return the Appointment location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param Location Sets the Appointment location.
     */
    public void setLocation(String Location) {
        location = Location;
    }

    /**
     * @return the Appointment type
     */
    public String getType() {
        return type;
    }

    /**
     * @param Type Sets the Appointment type.
     */
    public void setType(String Type) {
        type = Type;
    }

    /**
     * @return the Appointment start
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * @param Start Sets the Appointment start.
     */
    public void setStart(LocalDateTime Start) {
        start = Start;
    }

    /**
     * @return the Appointment end
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * @param End Sets the Appointment end.
     */
    public void setEnd(LocalDateTime End) {
        end = End;
    }

    /**
     * @return the Appointment create date
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * @param CreateDate Sets the Appointment create date.
     */
    public void setCreateDate(LocalDateTime CreateDate) {
        createDate = CreateDate;
    }

    /**
     * @return the Appointment created by
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param CreatedBy Sets the Appointment created by.
     */
    public void setCreatedBy(String CreatedBy) {
        createdBy = CreatedBy;
    }

    /**
     * @return the Appointment last update
     */
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    /**
     * @param LastUpdate Sets the Appointment last update.
     */
    public void setLastUpdate(LocalDateTime LastUpdate) {
        lastUpdate = LastUpdate;
    }

    /**
     * @return the Appointment last updated by
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * @param LastUpdatedBy Sets the Appointment last updated by.
     */
    public void setLastUpdatedBy(String LastUpdatedBy) {
        lastUpdatedBy = LastUpdatedBy;
    }

    /**
     * @return the Customer ID of the Appointment
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * @param CustomerID Sets the Customer ID of the Appointment.
     */
    public void setCustomerId(int CustomerID) {
        customerId = CustomerID;
    }

    /**
     * @return the User ID of the Appointment
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param UserID Sets the User ID of the Appointment.
     */
    public void setUserId(int UserID) {
        userId = UserID;
    }

    /**
     * @return the Contact ID of the Appointment
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * @param ContactID Sets the Contact ID of the Appointment.
     */
    public void setContactId(int ContactID) {
        contactId = ContactID;
    }

    /**
     * @return the Appointment String type
     */
    @Override
    public String toString() {
        return type;
    }
}