package Model;

import java.time.LocalDateTime;

/**
 * This is the class for creating user objects.
 *
 * @author Mehdi Rahimi
 */
public class User {
    /**
     * The currentUser variable is used to store the current user.
     */
    private static User currentUser;
    /**
     * The userId variable is used to store the user ID.
     */
    private int userId;
    /**
     * The userName variable is used to store the user name.
     */
    private String userName;
    /**
     * The password variable is used to store the user password.
     */
    private String password;
    /**
     * The createDate variable is used to store the date the user was created.
     */
    private LocalDateTime createDate;
    /**
     * The createdBy variable is used to store the user who created the user.
     */
    private String createdBy;
    /**
     * The lastUpdate variable is used to store the date the user was last updated.
     */
    private LocalDateTime lastUpdate;
    /**
     * The lastUpdateBy variable is used to store the user who last updated the user.
     */
    private String lastUpdateBy;

    /**
     * This is the constructor for the User class.
     *
     * @param userId       The user ID.
     * @param userName     The user name.
     * @param password     The user password.
     * @param createDate   The date the user was created.
     * @param createdBy    The user who created the user.
     * @param lastUpdate   The date the user was last updated.
     * @param lastUpdateBy The user who last updated the user.
     */
    public User(int userId, String userName, String password, LocalDateTime createDate, String createdBy,
                LocalDateTime lastUpdate, String lastUpdateBy) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
    }

    /**
     * This function returns the userId of the user.
     *
     * @return The userId.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * This function sets the userId of the user.
     *
     * @param userId The userId.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }


    /**
     * This function returns the userName of the user.
     *
     * @return The userName variable is being returned.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * This function sets the userName of the user.
     *
     * @param userName The userName variable is being set.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }


    /**
     * This function returns the password.
     *
     * @return The password
     */
    public String getPassword() {
        return password;
    }

    /**
     * This function sets the password.
     *
     * @param password The password
     */
    public void setPassword(String password) {
        this.password = password;
    }


    /**
     * This function returns the date and time that the user was created.
     *
     * @return The createDate variable is being returned.
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * This function sets the date and time that the user was created.
     *
     * @param createDate The createDate variable is being set.
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * This function returns the user who created the user.
     *
     * @return The createdBy variable is being returned.
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * This function sets the user who created the user.
     *
     * @param createdBy The createdBy variable is being set.
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * This function returns the date and time that the user was last updated.
     *
     * @return The lastUpdate variable is being returned.
     */
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    /**
     * This function sets the date and time that the user was last updated.
     *
     * @param lastUpdate The lastUpdate variable is being set.
     */
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * This function returns the user who last updated the user.
     *
     * @return The lastUpdateBy variable is being returned.
     */
    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    /**
     * This function sets the user who last updated the user.
     *
     * @param lastUpdateBy The lastUpdateBy variable is being set.
     */
    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    /**
     * This function returns the user as a string.
     *
     * @return The userName variable is being returned.
     */
    @Override
    public String toString() {
        return userName;
    }

    /**
     * This function returns the current user
     *
     * @return the current user
     */
    public static User getCurrentUser() {
        return currentUser;
    }

    /**
     * This function sets the current user
     *
     * @param user the current user
     */
    public static void setCurrentUser(User user) {
        currentUser = user;
    }
}