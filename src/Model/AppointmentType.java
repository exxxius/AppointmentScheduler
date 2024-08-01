package Model;

/**
 * This class creates the appointmentType objects.
 *
 * @author Mehdi Rahimi
 */
public class AppointmentType {
    /**
     * The appointmentType type.
      */
    private String Type;

    /**
     * This is the constructor for the AppointmentType class.
     *
     * @param Type The appointmentType type.
     */
    public AppointmentType(String Type) {
        this.Type = Type;
    }

    /**
     * @return the apponitmentType type
     */
    public String getType() {
        return Type;
    }

    /**
     * @param Type the apponitmentType type to set
     */
    public void setType(String Type) {
        Type = Type;
    }

    /**
     * @return the appointmentType String type
     */
    @Override
    public String toString() {
        return Type;
    }
}