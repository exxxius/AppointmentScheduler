package Model;

import java.time.LocalDateTime;

/**
 * This class represents the first level division entity.
 * A first level division belongs to a country and has a unique ID, name, and other attributes.
 */
public class FirstLevelDivision {
    /**
     * The ID of the first level division.
     */
    private int divisionId;
    /**
     * The name of the first level division.
     */
    private String division;
    /**
     * The date and time the division was created.
     */
    private LocalDateTime createDate;
    /**
     * The user who created the first level division.
     */
    private String createdBy;
    /**
     * The date and time the first level division was last updated.
     */
    private LocalDateTime lastUpdate;
    /**
     * The user who last updated the first level division.
     */
    private String lastUpdatedBy;
    /**
     * The ID of the country to which the first level division belongs.
     */
    private int countryId;

    /**
     * Constructs a new FirstLevelDivision object with the given attributes.
     *
     * @param divisionId    The ID of the first level division
     * @param division      The name of the first level division
     * @param createDate    The date and time the first level division was created
     * @param createdBy     The user who created the first level division
     * @param lastUpdate    The date and time the first level division was last updated
     * @param lastUpdatedBy The user who last updated the first level division
     * @param countryId     The ID of the country to which the first level division belongs
     */
    public FirstLevelDivision(int divisionId, String division, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy, int countryId) {
        this.divisionId = divisionId;
        this.division = division;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.countryId = countryId;
    }

    /**
     * Returns the ID of the first level division.
     *
     * @return The division ID
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * Sets the ID of the first level division.
     *
     * @param divisionId The division ID to set
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * Returns the name of the first level division.
     *
     * @return The division name
     */
    public String getDivision() {
        return division;
    }

    /**
     * Sets the name of the first level division.
     *
     * @param division The division name to set
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * Returns the date and time the first level division was created.
     *
     * @return The creation date and time
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * Sets the date and time the first level division was created.
     *
     * @param createDate The creation date and time to set
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * Returns the user who created the first level division.
     *
     * @return The creator's name
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the user who created the first level division.
     *
     * @param createdBy The creator's name to set
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Returns the date and time the first level division was last updated.
     *
     * @return The last update date and time
     */
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Sets the last update date of the division.
     *
     * @param lastUpdate Sets the last update date of the division.
     */
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Returns the user who last updated the division.
     *
     * @return the user who last updated the division
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Sets the user who last updated the division.
     *
     * @param lastUpdatedBy Sets the user who last updated the division.
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Returns the country ID of the division.
     *
     * @return the country ID of the division
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * Sets the ID of the country where the FirstLevelDivision belongs.
     *
     * @param countryId the ID of the country to set
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * Returns a string representation of the FirstLevelDivision object.
     *
     * @return the name of the FirstLevelDivision
     */
    @Override
    public String toString() {
        return division;
    }
}
