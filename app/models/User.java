package models;

import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.LinkedList;
import java.util.List;

/**
 * User entity managed by Ebean
 */
@Entity 
@Table(name="account")
public class User extends Model {

    @Id
    public Long id;

    @Constraints.Required
    @Formats.NonEmpty
    public String email;
    
    @Constraints.Required
    public String name;
    
    @Constraints.Required
    public String password;

    public List<App> userApps;
    
    // -- Queries
    
    public static Finder<String,User> find = new Finder(String.class, User.class);
    
    /**
     * Retrieve all users.
     */
    public static List<User> all() {
        return find.all();
    }

    /**
     * Retrieve a User from email.
     */
    public static User findByEmail(String email) {
        return find.where().eq("email", email).findUnique();
    }
    
    /**
     * Authenticate a User.
     */
    public static User authenticate(String email, String password) {
        return find.where()
            .eq("email", email)
            .eq("password", password)
            .findUnique();
    }

    public static void create(User user){
         user.userApps = new LinkedList<App>();
         user.save();
    }
    
    // --
    
    public String toString() {
        return "User(" + email + ")";
    }

}

