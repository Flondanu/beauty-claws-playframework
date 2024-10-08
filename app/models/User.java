package models;



import io.ebean.Finder;
import io.ebean.Model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.security.crypto.bcrypt.BCrypt;
import play.data.validation.Constraints;

import java.util.regex.Pattern;

@Entity
@Table(name = "users")
public class User extends Model {
    @Id
    public Integer id;

    @Constraints.Required(message = "username required")
    private String username;


    @Constraints.Required(message = "mobile required")
    private String mobile;

    @Constraints.Required(message = "email required")
    @Column(unique = true)
    private String email;

    @Constraints.Required(message = "password required")
    private String password;

    // Constructors
    public User() {}

    public User(String username, String email, String password, String mobile) {
        this.username = username;
        this.mobile = mobile;
        this.email = email;
        this.password = password;
    }

    public static final Finder<Integer, User> find = new Finder<>(User.class);


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;

    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile (String mobile) {
        this.mobile = mobile;
    }

    // Hash the password using BCrypt
    public void hashPassword() {
        this.password= BCrypt.hashpw(this.password, BCrypt.gensalt());
    }

    // Check if the provided password matches the hashed password
    public boolean checkPassword(String plainTextPassword) {

        return BCrypt.checkpw(plainTextPassword, this.password);
    }

    // Method to validate email format using a regular expression
    public boolean isValidEmailFormat(String email) {
        // Regular expression for a basic email format
        String emailRegex = "^[a-z0-9_+&-]+(?:\\.[a-z0-9_+&-]+)*@(?:[a-z0-9-]+\\.)+[a-z]{2,7}$\n";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
}