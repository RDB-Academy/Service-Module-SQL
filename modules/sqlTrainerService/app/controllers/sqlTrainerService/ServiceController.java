package controllers.sqlTrainerService;

import controllers.RootController;
import models.sqlTrainerService.UserData;
import services.sqlTrainerService.UserDataService;

import javax.inject.Inject;
import java.util.Map;

abstract class ServiceController extends RootController {
    private UserDataService userDataService;

    @Inject
    ServiceController(UserDataService userDataService) {

        this.userDataService = userDataService;
    }

    UserData getUserData(Map<String, Object> args) {
        return (UserData) args.get("UserData");
    }

}
