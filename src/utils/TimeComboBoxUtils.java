package utils;

import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This class provides utility methods for working with time-based ComboBoxes. The ComboBoxes are used to select the start
 * and end times of an appointment in the Appointments add and update screens. The ComboBoxes are initialized with
 * ZonedDateTime objects that represent the start and end times of an appointment. The ComboBoxes are displayed with
 * String values that represent the start and end hours and minutes of an appointment using a formatter.
 */
public class TimeComboBoxUtils {
    /**
     * The LOCAL_ZONE_ID variable is used to store the local time zone.
     */
    private static final ZoneId LOCAL_ZONE_ID = ZoneId.systemDefault();
    /**
     * The EASTERN_ZONE_ID variable is used to store the Eastern time zone.
     */
    private static final ZoneId EASTERN_ZONE_ID = ZoneId.of("America/New_York");
    /**
     * The FORMATTER variable is used to store the formatter used to display the start and end times of an appointment.
     */
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("hh:mm a");

    /**
     * The STRING_CONVERTER variable is used to convert the ZonedDateTime objects to String values.
     */
    private static final StringConverter<ZonedDateTime> STRING_CONVERTER = new StringConverter<ZonedDateTime>() {
        @Override
        public String toString(ZonedDateTime dateTime) {
            if (dateTime == null) {
                System.out.println("dateTime is null");
                return null;
            }
            return dateTime.format(FORMATTER);
        }

        @Override
        public ZonedDateTime fromString(String string) {
            return null;
        }
    };

    /**
     * Initializes a ComboBox with LocalTime objects that represent the start times of a business day.
     *
     * @param comboBox     the ComboBox to initialize
     * @param selectedDate the date for which to generate the start times
     */
    public static void initializeStartTimeComboBox(ComboBox<ZonedDateTime> comboBox, LocalDate selectedDate) {
        comboBox.getItems().clear();
        ZonedDateTime easternSelectedDateTime = ZonedDateTime.of(selectedDate, LocalTime.MIDNIGHT, EASTERN_ZONE_ID);
        ZonedDateTime easternStartTime = easternSelectedDateTime.with(LocalTime.of(8, 0));
        ZonedDateTime localStartTime = easternStartTime.withZoneSameInstant(LOCAL_ZONE_ID);

        while (localStartTime.isBefore(getEasternTimeWithZone(easternSelectedDateTime, 22).withZoneSameInstant(LOCAL_ZONE_ID))) {
            comboBox.getItems().add(localStartTime);
            localStartTime = localStartTime.plusMinutes(15);
        }

        comboBox.setConverter(STRING_CONVERTER);
    }

    /**
     * Initializes a ComboBox with LocalTime objects that represent the end times of a business day.
     *
     * @param comboBox          the ComboBox to initialize
     * @param selectedDate      the date for which to generate the end times
     * @param selectedStartTime the selected start time
     */
    public static void initializeEndTimeComboBox(ComboBox<ZonedDateTime> comboBox, LocalDate selectedDate, ZonedDateTime selectedStartTime) {
        comboBox.getItems().clear();
        ZonedDateTime easternSelectedDateTime = ZonedDateTime.of(selectedDate, LocalTime.MIDNIGHT, EASTERN_ZONE_ID);
        ZonedDateTime easternEndTime = easternSelectedDateTime.with(LocalTime.of(22, 0));
        LocalTime startTime = selectedStartTime.withZoneSameInstant(EASTERN_ZONE_ID).toLocalTime();

        while (startTime.isBefore(easternEndTime.toLocalTime())) {
            ZonedDateTime endTime = ZonedDateTime.of(easternSelectedDateTime.toLocalDate(), startTime.plusMinutes(15), EASTERN_ZONE_ID).withZoneSameInstant(LOCAL_ZONE_ID);
            comboBox.getItems().add(endTime);
            startTime = startTime.plusMinutes(15);
        }

        comboBox.setConverter(STRING_CONVERTER);
    }


    /**
     * Given a date and time, return a new date and time with the same date and the specified hour in the Eastern time
     * zone.
     *
     * @param dateTime The date and time to be converted to Eastern Time.
     * @param hour The hour of the day to set the time to.
     * @return A ZonedDateTime object with the same instant as the input dateTime, but with the hour set to the input hour
     * and the time zone set to Eastern Time.
     */
    private static ZonedDateTime getEasternTimeWithZone(ZonedDateTime dateTime, int hour) {
        return dateTime.with(LocalTime.of(hour, 0)).withZoneSameInstant(EASTERN_ZONE_ID);
    }
}