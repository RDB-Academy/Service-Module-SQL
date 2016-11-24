package services;

import models.SchemaDef;
import models.TableDef;
import play.data.Form;
import play.data.FormFactory;
import repository.TableDefRepository;
import services.tools.ServiceError;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author fabiomazzone
 */
@Singleton
public class TableDefService {
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

    public ServiceError deleteTableDef(Long id) {
        TableDef tableDef = this.tableDefRepository.getById(id);

        if(tableDef == null) {
            return new ServiceError(ServiceError.NotFound, "TableDef", id);
        }

        tableDef.delete();

        return null;
    }
}
