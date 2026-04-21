package com.booknest.config;

import com.booknest.book.entity.Book;
import com.booknest.book.repository.BookRepository;
import com.booknest.user.entity.Role;
import com.booknest.user.entity.User;
import com.booknest.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@booknest.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(Role.ADMIN);
            admin.setFirstName("Admin");
            admin.setLastName("User");
            userRepository.save(admin);

            User customer = new User();
            customer.setUsername("kartik");
            customer.setEmail("kartik@example.com");
            customer.setPassword(passwordEncoder.encode("password"));
            customer.setRole(Role.CUSTOMER);
            customer.setFirstName("Kartik");
            customer.setLastName("User");
            userRepository.save(customer);
        }

        if (bookRepository.count() == 0) {
            Book b1 = new Book(null, "The Great Gatsby", "F. Scott Fitzgerald", "9780743273565", 
                "A story of wealth, love, and the American Dream in the 1920s.", "Classic", 
                new BigDecimal("499.00"), 50, "https://images.unsplash.com/photo-1543004218-2c1402444b0d?auto=format&fit=crop&q=80&w=400", true);
            
            Book b2 = new Book(null, "Atomic Habits", "James Clear", "9780735211292", 
                "An easy and proven way to build good habits and break bad ones.", "Self-Help", 
                new BigDecimal("650.00"), 100, "https://images.unsplash.com/photo-1589998059171-988d887df646?auto=format&fit=crop&q=80&w=400", true);

            Book b3 = new Book(null, "The Alchemist", "Paulo Coelho", "9780062315007", 
                "A fable about following your dream.", "Adventure", 
                new BigDecimal("350.00"), 75, "https://images.unsplash.com/photo-1544947950-fa07a98d237f?auto=format&fit=crop&q=80&w=400", true);

            Book b4 = new Book(null, "Deep Work", "Cal Newport", "9781455586691", 
                "Rules for focused success in a distracted world.", "Business", 
                new BigDecimal("599.00"), 40, "https://images.unsplash.com/photo-1512820790803-83ca734da794?auto=format&fit=crop&q=80&w=400", false);

            Book b5 = new Book(null, "1984", "George Orwell", "9780451524935", 
                "A dystopian social science fiction novel.", "Dystopian", 
                new BigDecimal("399.00"), 60, "https://images.unsplash.com/photo-1541963463532-d68292c34b19?auto=format&fit=crop&q=80&w=400", false);

            Book b6 = new Book(null, "The Psychology of Money", "Morgan Housel", "9789390166268", 
                "Timeless lessons on wealth, greed, and happiness.", "Finance", 
                new BigDecimal("450.00"), 80, "https://images.unsplash.com/photo-1592492159418-39f319320569?auto=format&fit=crop&q=80&w=400", true);

            bookRepository.saveAll(Arrays.asList(b1, b2, b3, b4, b5, b6));
        }
    }
}
