package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import database.BookingDAO;
import database.CustomerDAO;
import database.ReviewDAO;
import model.Booking;
import model.Customer;
import model.Reviews;

import java.util.List;

public class CustomerService {
    private final CustomerDAO customerDAO = new CustomerDAO();
    private final BookingDAO bookingDAO = new BookingDAO();
    private final ReviewDAO reviewDAO = new ReviewDAO();
    private final ObjectMapper mapper = new ObjectMapper();

    public List<Customer> getAllCustomers() {
        return customerDAO.getAllCustomers();
    }

    public Customer getCustomerById(int id) {
        return customerDAO.getCustomerById(id);
    }

    public String getBookingsByCustomerIdAsJson(int customerId) {
        try {
            return mapper.writeValueAsString(bookingDAO.getBookingsByCustomerId(customerId));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{\"error\":\"Failed to convert bookings to JSON\"}";
        }
    }

    public String getReviewsByCustomerBookingAsJson(int customerId, int bookingId) {
        Reviews review = reviewDAO.getReviewByCustomerBooking(customerId, bookingId);
        if (review != null) {
            try {
                return mapper.writeValueAsString(review);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return "{\"error\": \"Not Found\"}";
    }

    public void createCustomer(Customer customer) {
        customerDAO.insertCustomer(customer);
    }

    public void createBooking(int customerId, Booking booking) {
        Customer customer = customerDAO.getCustomerById(customerId);
        if (customer == null) {
            throw new IllegalArgumentException("Customer not found");
        }
        booking.setCustomer(customerId);
        bookingDAO.insertBooking(booking);
    }

    public void createReview(int bookingId, Reviews review) {
        review.setBooking(bookingId);
        reviewDAO.createReview(review);
    }

    public void updateCustomer(int id, Customer updatedCustomer) {
        updatedCustomer.setId(id);
        customerDAO.updateCustomer(updatedCustomer);
    }

}
