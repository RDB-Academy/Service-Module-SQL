package services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import models.ForeignKey;
import play.data.Form;
import play.data.FormFactory;
import repository.ForeignKeyRepository;

/**
 * @author fabiomazzone
 */
@Singleton
public class ForeignKeyService {
    private final ForeignKeyRepository foreignKeyRepository;
    private final FormFactory formFactory;

    @Inject
    public ForeignKeyService(ForeignKeyRepository foreignKeyRepository, FormFactory formFactory) {

        this.foreignKeyRepository = foreignKeyRepository;
        this.formFactory = formFactory;
    }


    public Form<ForeignKey> readAsForm(Long id) {
        ForeignKey foreignKey = this.read(id);
        Form<ForeignKey> foreignKeyForm = getForm();

        if (foreignKey == null) {
            foreignKeyForm.reject(Service.formErrorNotFound, "ForeignKey not found");
            return foreignKeyForm;
        }

        return this.formFactory.form(ForeignKey.class).fill(foreignKey);
    }

    public ForeignKey read(Long id) {

        return this.foreignKeyRepository.getById(id);
    }

    private Form<ForeignKey> getForm() {
        return this.formFactory.form(ForeignKey.class);
    }

    public Form<ForeignKey> delete(Long id) {
        ForeignKey foreignKey = this.read(id);
        Form<ForeignKey> foreignKeyForm = getForm();

        if(foreignKey == null) {
            foreignKeyForm.reject(Service.formErrorNotFound, "ForeignKey not Found");
            return foreignKeyForm;
        }

        foreignKey.delete();

        return foreignKeyForm;
    }
}
