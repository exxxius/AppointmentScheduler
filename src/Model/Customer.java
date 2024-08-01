package Model;

import java.time.LocalDateTime;

/**
 * This is the class for creating customer objects.
 *
 * @author Mehdi Rahimi
 */
public class Customer {
    /**
     * The customer ID.
     */
    private int customerId;
    /**
     * The customer name.
     */
    private String customerName;
    /**
     * The customer address.
     */
    private String customerAddress;
    /**
     * The customer postal code.
     */
    private String postalCode;
    /**
     * The customer phone number.
     */
    private String customerPhone;
    /**
     * The date the customer was created.
     */
    private LocalDateTime createDate;
    /**
     * The user who created the customer.
     */
    private String createdBy;
    /**
     * The date the customer was last updated.
     */
    private LocalDateTime lastUpdate;
    /**
     * The user who last updated the customer.
     */
    private String lastUpdateBy;
    /**
     * The division ID.
     */
    private int divisionId;

    /**
     * This is the constructor for the Customer class.
     *
     * @param customerId      The customer ID.
     * @param customerName    The customer name.
     * @param customerAddress The customer address.
     * @param postalCode      The customer postal code.
     * @param customerPhone   The customer phone number.
     * @param createDate      The date the customer was created.
     * @param createdBy       The user who created the customer.
     * @param lastUpdate      The date the customer was last updated.
     * @param lastUpdateBy    The user who last updated the customer.
     * @param divisionId      The division ID.
     */
    public Customer(int customerId, String customerName, String customerAddress, String postalCode, String customerPhone,
                    LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdateBy, int divisionId) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.postalCode = postalCode;
        this.customerPhone = customerPhone;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
        this.divisionId = divisionId;
    }


    /**
     * This function returns the customerId of the customer.
     *
     * @return The customerId.
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * This function sets the customerId of the customer.
     *
     * @param customerID The customerId.
     */
    public void setCustomerId(int customerID) {
        this.customerId = customerID;
    }

    /**
     * This function returns the customerName of the customer.
     *
     * @return The customerName.
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * This function sets the customerName of the customer.
     *
     * @param customerName The customerName.
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * This function returns the customerAddress of the customer.
     *
     * @return The customerAddress.
     */
    public String getCustomerAddress() {
        return customerAddress;
    }

    /**
     * This function sets the customerAddress of the customer.
     *
     * @param customerAddress The customerAddress
     */
    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    /**
     * This function returns the postalCode of the customer.
     *
     * @return The postalCode
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * This function sets the postalCode of the customer.
     *
     * @param postalCode The postalCode
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * This function returns the phone number of the customer.
     *
     * @return The customerPhone
     */
    public String getCustomerPhone() {
        return customerPhone;
    }

    /**
     * This function sets the phone number of the customer.
     *
     * @param customerPhone The customerPhone.
     */
    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    /**
     * @return the Customer create date.
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }


    /**
     * This function sets the createDate variable to the value of the createDate parameter.
     *
     * @param createDate The date and time the record was created.
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }


    /**
     * This function returns the value of the createdBy field.
     *
     * @return The createdBy variable is being returned.
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy Sets the Customer created by.
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return the Customer last update.
     */
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    /**
     * @param lastUpdate Sets the Customer last update.
     */
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }


    /**
     * This function returns the lastUpdateBy field.
     *
     * @return The lastUpdateBy variable is being returned.
     */
    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    /**
     * @param lastUpdateBy Sets the Customer last update by.
     */
    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    /**
     * @return the Division ID of the Customer.
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * @param divisionID Sets the Division ID of the Customer.
     */
    public void setDivisionId(int divisionID) {
        this.divisionId = divisionID;
    }

    /**
     * This function returns the customerName of the customer.
     *
     * @return The customerName.
     */
    @Override
    public String toString() {
        return customerName;
    }
}
