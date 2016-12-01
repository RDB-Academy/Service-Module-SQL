package controllers.admin;

import authenticators.Authenticated;
import models.ColumnDef;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import services.ColumnDefService;

import javax.inject.Inject;

/**
 * Created by nicolenaczk on 24.11.16.
 */
@Security.Authenticated(Authenticated.class)
public class ColumnDefController extends Controller {
    private final ColumnDefService columnDefService;

    @Inject
    public ColumnDefController(ColumnDefService columnDefService){

        this.columnDefService = columnDefService;

    }

}