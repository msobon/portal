package controllers;

import models.User;
import play.mvc.Controller;
import play.mvc.Result;


public class Accounting extends Controller {

    public static Result chargeUser(String email, Long chargeValue) {
        User user = User.findByEmail(email);
        if (user.credits < chargeValue) {
            return ok("false");
        }
        user.credits -= chargeValue;
        user.save();
        System.out.println("charged: " + email + " " + chargeValue);

        return ok("true");
    }


}
