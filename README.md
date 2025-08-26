### AppointmentScheduler

**Description:**
The AppointmentScheduler project is a scheduling application designed for managing appointments and customer information. This application allows users to schedule appointments, manage customer data, and make reports. The business hours are 8:00 to 22:00 in Eastern Time Zone (New York). All dates and times are displayed in user's local timezone, however, all dates and times are converted into UTC Standard time zone format by the database driver manager, and saved into the database.
	

**Features:**
- **User Authentication:** Secure login system for users.
- **Customer Management:** CRUD operations for managing customer information.
- **Appointment Management:** Schedule, modify, and delete appointments.
- **Reports:** Generate various reports on appointments and customer interactions.
- **Localization:** Supports multiple languages (EN, FR).

**Project Structure:**
- **Controller:** Handles user input and interactions.
- **DAO:** Data Access Objects for database interactions.
- **Model:** Defines the data structures.
- **Utils:** Utility classes and functions.
- **View:** FXML files for the UI.

**Technologies Used:**
- Java
- JavaFX for the UI
- MySQL for database management
- Maven for project management

**Setup Instructions:**
1. Clone the repository: `git clone https://github.com/exxxius/AppointmentScheduler.git`
2. Open the project in your preferred IDE (IntelliJ IDEA recommended).
3. Configure the database connection in the `utils/JDBC.java` file.
4. Build and run the project.

**Contributing:**
Contributions are welcome! Please fork the repository and submit pull requests.

For more details, visit the [repository](https://github.com/exxxius/AppointmentScheduler).
