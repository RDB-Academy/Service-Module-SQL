package services;

import models.TableDef;
import play.data.Form;
import repository.TableDefRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author fabiomazzone
 */
@Singleton
public class TableDefService {
    private TableDefRepository tableDefRepository;

    @Inject
    public TableDefService(
            TableDefRepository tableDefRepository) {

        this.tableDefRepository = tableDefRepository;
    }

    public Form<TableDef> getViewForm(Long id) {
        TableDef tableDef = this.tableDefRepository.getById(id);

        return null;
    }
}
