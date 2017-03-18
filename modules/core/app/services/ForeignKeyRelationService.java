package services;

import forms.ForeignKeyRelationForm;
import models.ForeignKeyRelation;
import play.data.Form;
import play.data.FormFactory;
import repository.ForeignKeyRelationRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author fabiomazzone
 */
@Singleton
public class ForeignKeyRelationService {
    private final ForeignKeyRelationRepository foreignKeyRelationRepository;
    private final FormFactory formFactory;

    @Inject
    public ForeignKeyRelationService(ForeignKeyRelationRepository foreignKeyRelationRepository, FormFactory formFactory) {
        this.foreignKeyRelationRepository = foreignKeyRelationRepository;
        this.formFactory = formFactory;
    }

    public ForeignKeyRelation read(Long id) {
        return foreignKeyRelationRepository.getById(id);
    }

    public Form<ForeignKeyRelation> readAsForm(Long id) {
        ForeignKeyRelation foreignKeyRelation = this.read(id);
        Form<ForeignKeyRelation> foreignKeyRelationForm = getForm();

        if (foreignKeyRelation == null) {
            foreignKeyRelationForm.reject(Service.formErrorNotFound, "ForeignKeyRelation not found");
            return foreignKeyRelationForm;
        }

        return this.formFactory.form(ForeignKeyRelation.class).fill(foreignKeyRelation);
    }

    private Form<ForeignKeyRelation> getForm() {
        return this.formFactory.form(ForeignKeyRelation.class);
    }

    public Form<ForeignKeyRelationForm> getSimpleForm() {
        return this.formFactory.form(ForeignKeyRelationForm.class);
    }
}
