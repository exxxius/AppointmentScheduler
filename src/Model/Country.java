package Model;

import java.time.LocalDateTime;

/**
 * The Country class is a Java class that represents a country
 */
public class Country {
    /**
     * The country ID.
     */
    private int countryId;
    /**
     * The country name.
     */
    private String country;
    /**
     * The date and time the country info was created.
     */
    private LocalDateTime createDate;
    /**
     * The name of the user who created the country info.
     */
    private String createdBy;
    /**
     * The date and time the country info was last updated.
     */
    private LocalDateTime lastUpdate;
    /**
     * The name of the user who last updated the country info.
     */
    private String lastUpdatedBy;

    /**
     * Constructs a Country object with the specified parameters.
     *
     * @param countryId     The ID of the country.
     * @param countryName   The name of the country.
     * @param createDate    The date and time the country was created.
     * @param createdBy     The name of the user who created the country.
     * @param lastUpdate    The date and time the country was last updated.
     * @param lastUpdatedBy The name of the user who last updated the country.
     */
    public Country(int countryId, String countryName, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy) {
        this.countryId = countryId;
        this.country = countryName;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }


    /**
     * > This function returns the countryId of the current object.
     *
     * @return The countryId
     */
    public int getCountryId() {
        return countryId;
    }


    /**
     * This function sets the countryId of the object to the value of the parameter countryId.
     *
     * @param countryId The ID of the country you want to get the states for.
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * Returns the name of the country.
     *
     * @return the name of the country.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the name of the country.
     *
     * @param countryName the name of the country to set.
     */
    public void setCountry(String countryName) {
        this.country = country;
    }

    /**
     * Returns the date and time the country was created.
     *
     * @return the date and time the country was created.
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * Sets the date and time the country was created.
     *
     * @param createDate the date and time the country was created to set.
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * Returns the name of the user who created the country.
     *
     * @return the name of the user who created the country.
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the name of the user who created the country.
     *
     * @param createdBy the name of the user who created the country to set.
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Returns the date and time the country was last updated.
     *
     * @return the date and time the country was last updated.
     */
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Sets the date and time the country was last updated.
     *
     * @param lastUpdate the date and time the country was last updated to set.
     */
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Sets the name of the user who last updated the country.
     * @param lastUpdatedBy the name of the user who last updated the country to set.
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * @return the Country String name
     */
    @Override
    public String toString() {
        return country;
    }
}