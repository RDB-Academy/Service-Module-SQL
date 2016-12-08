package services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import models.ForeignKey;
import repository.ForeignKeyRepository;

/**
 * @author fabiomazzone
 */
@Singleton
public class ForeignKeyService {
    private final ForeignKeyRepository foreignKeyRepository;

    @Inject
    public ForeignKeyService(
            ForeignKeyRepository foreignKeyRepository
    ) {
        this.foreignKeyRepository = foreignKeyRepository;
    }


    public ForeignKey read(Long id) {
        return this.foreignKeyRepository.getById(id);
    }
}
