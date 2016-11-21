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
public class TableDefService {
    private TableDefRepository tableDefRepository;
    private FormFactory formFactory;

    @Inject
    public TableDefService(
            TableDefRepository tableDefRepository,
            FormFactory formFactory) {

        this.tableDefRepository = tableDefRepository;
        this.formFactory = formFactory;
    }

    public Form<TableDef> getViewForm(Long id) {
        TableDef tableDef = this.tableDefRepository.getById(id);



        return null;
    }
}
