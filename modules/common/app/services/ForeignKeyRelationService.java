package services;

import models.ForeignKeyRelation;
import repository.ForeignKeyRelationRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author fabiomazzone
 */
@Singleton
public class ForeignKeyRelationService {
    private final ForeignKeyRelationRepository foreignKeyRelationRepository;

    @Inject
    public ForeignKeyRelationService(
            ForeignKeyRelationRepository foreignKeyRelationRepository) {

        this.foreignKeyRelationRepository = foreignKeyRelationRepository;
    }

    public ForeignKeyRelation read(Long id) {
        return foreignKeyRelationRepository.getById(id);
    }
}
