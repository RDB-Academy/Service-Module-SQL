package services;

import models.TableDef;
import play.data.Form;
import play.data.FormFactory;
import repository.TableDefRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

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

    public Form<TableDef> getViewForm(Long id) {
        TableDef tableDef = this.tableDefRepository.getById(id);

            if (tableDef == null) {
            return null;
        }

        Form<TableDef> tableDefForm = this.formFactory.form(TableDef.class).fill(tableDef);

        return tableDefForm;
    }

    public TableDef getById(Long id) {

        return this.tableDefRepository.getById(id);

    }

    public Form<TableDef> deleteTableDef(Long id) {
        TableDef tableDef = this.tableDefRepository.getById(id);
        Form<TableDef> tableDefForm = this.formFactory.form(TableDef.class);

        if(tableDef == null) {
            tableDefForm.reject(Service.formErrorNotFound, "Table not Found");
            return tableDefForm;
        }

        tableDef.delete();

        return tableDefForm;
    }
}
