package models;

import com.avaje.ebean.Ebean;
import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;
import play.db.jpa.Transactional;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * User entity managed by Ebean
 */
@Entity
@Table(name = "account")
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

    public String ssoToken;

    public Long credits = 0l;

    public boolean isAdmin = false;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_requestedApps", joinColumns = @JoinColumn(name = "email"),
            inverseJoinColumns = @JoinColumn(name = "id"))
    public List<App> requestedApps = new ArrayList<App>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_userApps", joinColumns = @JoinColumn(name = "email"),
            inverseJoinColumns = @JoinColumn(name = "id"))
    public List<App> userApps = new ArrayList<App>();


    // -- Queries

    public static Finder<String, User> find = new Finder(String.class, User.class);

    public static Finder<Long, User> findById = new Finder(Long.class, User.class);

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

    public static boolean validateToken(String email, String token) {
        if("".equals(token)) return false;
        return find.where().eq("email", email).eq("ssoToken", token).findUnique() != null;
    }

    /**
     * Authenticate a User.
     */
    public static String authenticate(String email, String password) {
        User user = find.where()
                .eq("email", email)
                .eq("password", password)
                .findUnique();

        if (user == null) return null;
        else {
            Calendar calendar = Calendar.getInstance();
            user.ssoToken = "" + calendar.getTimeInMillis() + calendar.hashCode();
            user.save();

            return user.ssoToken;
        }
    }

    public static void create(User user) {
        user.save();
    }

    public static void delete(Long id) {
        findById.ref(id).delete();
    }

    @Transactional
    public static void approveRequestedApp(String email, Long appId) {
        User user = User.findByEmail(email);
        App requestedApp = App.find.ref(appId);
        user.requestedApps.remove(requestedApp);

        user.saveManyToManyAssociations("requestedApps");
        if (!user.userApps.contains(requestedApp)) {
            user.userApps.add(requestedApp);
            Ebean.saveManyToManyAssociations(user, "userApps");
        }

    }

    @Transactional
    public static boolean chargeUser(String email, long chargeValue) {
        User user = User.findByEmail(email);
        if (user.credits >= chargeValue) {
            user.credits -= chargeValue;
            user.save();

            return true;
        } else {
            return false;
        }
    }

    // --

    public String toString() {
        return "User(" + email + ")";
    }

}

