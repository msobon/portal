package controllers;

import models.User;
import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;


public class Accounting extends Controller {

    public static Result chargeUser(String email, String token, Long chargeValue) {
        User user = User.findByEmail(email);

        if (user.credits < chargeValue) {
            Logger.debug("Unnable to charge user " + user.email + " " + chargeValue);
            return ok("false");
        }

        if (!user.ssoToken.equals(token)) {
            Logger.debug("Invalid token for user " + user.email);
            return ok("false");
        }
        user.credits -= chargeValue;
        user.save();
        Logger.debug("User charged: " + email + " " + chargeValue);

        return ok("true");
    }


}
