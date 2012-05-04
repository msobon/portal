package models;

import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="app")
public class App extends Model {

    @Id
    public Long id;

    @Constraints.Required
    @Formats.NonEmpty
    public String uri;

    @Constraints.Required
    public String name;

    @ManyToMany
    public List<User> users = new ArrayList<User>();

    public static Finder<Long,App> find = new Finder(
            Long.class, App.class
    );

    public static List<App> all() {
        return find.all();
    }

    public static void create(App app) {
        app.save();
    }

    public static void delete(Long id) {
        find.ref(id).delete();
    }

}
