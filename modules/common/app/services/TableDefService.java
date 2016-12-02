package services;

import com.google.common.collect.ImmutableMap;
import models.TableDef;
import play.data.Form;
import play.data.FormFactory;
import repository.TableDefRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Map;

/**
 * @author fabiomazzone
 */
@Singleton
public class TableDefService extends Service {
    private TableDefRepository tableDefRepository;
    private FormFactory formFactory;

    @Inject
    public TableDefService(
            TableDefRepository tableDefRepository, FormFactory formFactory) {

        this.tableDefRepository = tableDefRepository;
        this.formFactory = formFactory;
    }

    public TableDef read(Long id) {
        return this.tableDefRepository.getById(id);
    }

    public Form<TableDef> readAsForm(Long id) {
        TableDef tableDef = this.read(id);
        Form<TableDef> tableDefForm = getForm();

        if (tableDef == null) {
            tableDefForm.reject(Service.formErrorNotFound, "TableDef not found");
            return tableDefForm;
        }

        return this.formFactory.form(TableDef.class).fill(tableDef);
    }

    public Form<TableDef> update(Long id) {
        TableDef tableDef = this.read(id);
        Form<TableDef> tableDefForm = this.getForm().bindFromRequest();

        if(tableDef == null) {
            tableDefForm.reject(Service.formErrorNotFound, "TableDef not found");
            return tableDefForm;
        }

        if(tableDefForm.hasErrors()) {
            return tableDefForm;
        }

        if(tableDefForm.get().getName() == null || tableDefForm.get().getName().isEmpty()) {
            tableDefForm.reject("Name", "TableDef must be named");
            return tableDefForm;
        }

        tableDef.setName(tableDefForm.get().getName());

        this.tableDefRepository.save(tableDef);

        return tableDefForm;
    }

    public Form<TableDef> delete(Long id) {
        TableDef tableDef = this.read(id);
        Form<TableDef> tableDefForm = getForm();

        if(tableDef == null) {
            tableDefForm.reject(Service.formErrorNotFound, "Table not Found");
            return tableDefForm;
        }

        tableDef.delete();

        return tableDefForm;
    }


    /**
     * A Helper Function to create new TableDef Forms
     * @return a new TableDef Form
     */
    private Form<TableDef> getForm() {
        return this.formFactory.form(TableDef.class);
    }
}
