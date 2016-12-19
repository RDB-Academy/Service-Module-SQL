package services;

import models.ColumnDef;
import play.data.FormFactory;
import repository.ColumnDefRepository;
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

    private Form<ColumnDef> getForm() {

        return this.formFactory.form(ColumnDef.class);
    }

    public Form<ColumnDef> addColumn(Long id) {
        ColumnDef columnDef = this.read(id);
        Form<ColumnDef> columnDefForm = this.getForm().bindFromRequest();

        if(columnDef == null) {
            columnDefForm.reject(Service.formErrorNotFound, "ColumnDef not found");
            return columnDefForm;
        }

        if(columnDefForm.hasErrors()) {
            return columnDefForm;
        }

        if(columnDefForm.get().getName() == null || columnDefForm.get().getName().isEmpty()) {
            columnDefForm.reject("Name", "ColumnDef must be named");
            return columnDefForm;
        }

        columnDef.setName(columnDefForm.get().getName());

        this.columnDefRepository.save(columnDef);

        return columnDefForm;
    }
}