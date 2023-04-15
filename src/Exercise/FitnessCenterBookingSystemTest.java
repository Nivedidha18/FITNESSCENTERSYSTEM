package Exercise;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Map;

public class FitnessCenterBookingSystemTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testInitializeTimetable() {
        FitnessCenterBookingSystem.initializeTimetable();
        Map<String, Map<String, Double>> timetable = FitnessCenterBookingSystem.timetable;
        Assertions.assertEquals(4, timetable.size());
        Assertions.assertTrue(timetable.containsKey("yoga"));
        Assertions.assertTrue(timetable.containsKey("pilates"));
        Assertions.assertTrue(timetable.containsKey("cardio"));
        Assertions.assertTrue(timetable.containsKey("zumba"));
        Assertions.assertEquals(2, timetable.get("yoga").size());
        Assertions.assertEquals(2, timetable.get("pilates").size());
        Assertions.assertEquals(2, timetable.get("cardio").size());
        Assertions.assertEquals(2, timetable.get("zumba").size());
        Assertions.assertEquals(10.0, timetable.get("yoga").get("Saturday"));
        Assertions.assertEquals(12.0, timetable.get("yoga").get("Sunday"));
        Assertions.assertEquals(8.0, timetable.get("pilates").get("Saturday"));
        Assertions.assertEquals(12.0, timetable.get("pilates").get("Sunday"));
        Assertions.assertEquals(8.0, timetable.get("cardio").get("Saturday"));
        Assertions.assertEquals(15.0, timetable.get("cardio").get("Sunday"));
        Assertions.assertEquals(15.0, timetable.get("zumba").get("Saturday"));
        Assertions.assertEquals(15.0, timetable.get("zumba").get("Sunday"));
    }

    @Test
    public void testDisplayTimetableForDay() {
        FitnessCenterBookingSystem.initializeTimetable();
        FitnessCenterBookingSystem.displayTimetableForDay("Saturday");
        String expectedOutput = "yoga class on Saturday at 10.0 dollars\n" +
                "pilates class on Saturday at 8.0 dollars\n" +
                "cardio class on Saturday at 8.0 dollars\n" +
                "zumba class on Saturday at 15.0 dollars\n";
        Assertions.assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void testGetTimetableForFitnessTypeAndTimeslot() {
        FitnessCenterBookingSystem.initializeTimetable();
        Map<String, Double> timetable = FitnessCenterBookingSystem.getTimetableForFitnessTypeAndTimeslot("yoga", "morning");
        Assertions.assertEquals(10.0, timetable.get("Saturday"));
        Assertions.assertEquals(10.0, timetable.get("Sunday"));
        timetable = FitnessCenterBookingSystem.getTimetableForFitnessTypeAndTimeslot(null, "morning");
        Assertions.assertEquals(10.0, timetable.get("yoga"));
        Assertions.assertEquals(8.0, timetable.get("pilates"));
        Assertions.assertEquals(8.0, timetable.get("cardio"));
        Assertions.assertEquals(10.0, timetable.get("zumba"));
    }

    @Test
    
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testBooking() {
        List<Booking> bookings = new ArrayList<>();
        Scanner sc = new Scanner("Alice\nyoga\n2023-05-01\nmorning\n");
        FitnessCenterBookingSystem.Booking(sc, bookings);
        Assertions.assertEquals(1, bookings.size());
        Booking booking = bookings.get(0);
        Assertions.assertEquals("Alice", booking.getName());
        Assertions.assertEquals("yoga", booking.getFitnessType());
        Assertions.assertEquals(LocalDate.of(2023, 5, 1), booking.getDate());
        Assertions.assertEquals("morning", booking.getTimeSlot());
    }

    @Test
    public void testViewBookingsofthelessonEmpty() {
        List<Booking> bookings = new ArrayList<>();
        FitnessCenterBookingSystem.viewBookingsofthelesson(bookings);
        Assertions.assertEquals("No bookings found.\n", outContent.toString());
    }

    @Test
    public void testViewBookingsofthelessonNotEmpty() {
        List<Booking> bookings = new ArrayList<>();
        Booking booking1 = new Booking(1, "Alice", LocalDate.of(2023, 5, 1), "yoga", "morning");
        Booking booking2 = new Booking(2, "Bob", LocalDate.of(2023, 5, 2), "pilates", "afternoon");
        bookings.add(booking1);
        bookings.add(booking2);
        FitnessCenterBookingSystem.viewBookingsofthelesson(bookings);
        String expectedOutput = String.format(" %-20s %-20s %-15s %-15s %n", "Booking ID", "Name", "Fitness Type", "Time Slot") +
                String.format(" %-20s %-20s %-15s %-15s %n", booking1.getId(), booking1.getName(), booking1.getFitnessType(), booking1.getTimeSlot()) +
                String.format(" %-20s %-20s %-15s %-15s %n", booking2.getId(), booking2.getName(), booking2.getFitnessType(), booking2.getTimeSlot());
        Assertions.assertEquals(expectedOutput, outContent.toString());
    }
    @Test
    void testAttendLessonAndLeaveReview_NotAttended() {
        // Arrange
        InputStream input = new ByteArrayInputStream("1\nn".getBytes());
        System.setIn(input);

        // Act
        FitnessCenter.attendLessonAndLeaveReview(new Scanner(System.in));

        // Assert
        Assertions.assertEquals(0, bookings.get(0).getRating());
        Assertions.assertEquals("", bookings.get(0).getReview());
    }
    @Test
    void testPrintNumBookingsAndAverageRatingByFitnessType() {
        // Arrange
        bookings.add(new Booking(3, "yoga", "sunday", "Morning", Booking.BookingStatus.BOOKED, 4, ""));
        bookings.add(new Booking(4, "pilates", "saturday", "afternoon", Booking.BookingStatus.BOOKED, 3, ""));
        bookings.add(new Booking(5, "cardio", "sunday", "evening", Booking.BookingStatus.BOOKED, 5, ""));
        bookings.add(new Booking(6, "zumba", "saturday", "morning", Booking.BookingStatus.BOOKED, 2, ""));

        // Act
        FitnessCenter.printNumBookingsAndAverageRatingByFitnessType();

        // Assert
        Assertions.assertEquals("yoga: 2 bookings, 2.50 average rating", FitnessCenter.consoleOutput.get(0));
        Assertions.assertEquals("pilates: 2 bookings, 2.00 average rating", FitnessCenter.consoleOutput.get(1));
        Assertions.assertEquals("cardio: 1 bookings, 5.00 average rating", FitnessCenter.consoleOutput.get(2));
        Assertions.assertEquals("Zumba: 1 bookings, 2.00 average rating", FitnessCenter.consoleOutput.get(3));
    }
    @Before
    public void setUp() {
        // create some sample bookings for testing
        bookings = new ArrayList<>();
        bookings.add(new Booking(LocalDate.of(2023, 1, 1), "yoga", 50, 4));
        bookings.add(new Booking(LocalDate.of(2023, 1, 2), "yoga", 50, 3));
        bookings.add(new Booking(LocalDate.of(2023, 1, 3), "yoga", 50, 5));
        bookings.add(new Booking(LocalDate.of(2023, 1, 3), "zumba", 60, 2));
        bookings.add(new Booking(LocalDate.of(2023, 2, 1), "yoga", 50, 5));
        bookings.add(new Booking(LocalDate.of(2023, 2, 2), "zumba", 60, 4));
    }

    @Test
    public void testPrintAverageRatingByMonth() {
        // create a mock scanner with input "01" for January
        java.io.ByteArrayInputStream input = new java.io.ByteArrayInputStream("01\n".getBytes());
        java.util.Scanner sc = new java.util.Scanner(input);

        // call the method and get the output
        java.io.ByteArrayOutputStream output = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(output));
        FitnessBookingSystem.printAverageRatingByMonth(sc);
        String result = output.toString();

        // assert that the output is as expected
        String expected = "Number of bookings and average rating by fitness type:\n"
                + "yoga: 3 bookings, 4.00 average rating\n" + "boxing: 1 bookings, 2.00 average rating\n";
        assertEquals(expected, result);
    }

    @Test
    public void testPrintIncomeByMonth() {
        // create a mock scanner with input "02" for February
        java.io.ByteArrayInputStream input = new java.io.ByteArrayInputStream("02\n".getBytes());
        java.util.Scanner sc = new java.util.Scanner(input);

        // call the method and get the output
        java.io.ByteArrayOutputStream output = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(output));
        FitnessBookingSystem.printIncomeByMonth(sc);
        String result = output.toString();

        // assert that the output is as expected
        String expected = "Income by fitness type in month 02:\n" + "yoga: 50.00\n" + "zumba: 60.00\n"
                + "The highest income was generated by yoga lessons: 50.00\n";
        assertEquals(expected, result);
    }
}







