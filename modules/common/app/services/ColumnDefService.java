package services;

import play.data.FormFactory;
import repository.ColumnDefRepository;
import models.ColumnDef;
import play.data.Form;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by nicolenaczk on 24.11.16.
 */
@Singleton
public class ColumnDefService {
    private ColumnDefRepository columnDefRepository;
    private FormFactory formFactory;

    @Inject
    public ColumnDefService (ColumnDefRepository columnDefRepository, FormFactory formFactory){

        this.columnDefRepository = columnDefRepository;
        this.formFactory = formFactory;
    }

    public Form<ColumnDef> getViewForm(Long id) {
        ColumnDef columnDef = read(id);

        Form<ColumnDef> columnDefForm = this.formFactory.form(ColumnDef.class).fill(columnDef);

        return columnDefForm;
    }


    public ColumnDef read(Long id) {
        return this.columnDefRepository.getById(id);
    }
}