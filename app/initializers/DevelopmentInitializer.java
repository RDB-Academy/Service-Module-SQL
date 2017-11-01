package initializers;

import initializers.schemaBuilders.*;
import models.SchemaDef;
import models.UserProfile;
import repositories.SchemaDefRepository;
import repositories.UserProfileRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
class DevelopmentInitializer {
    private SchemaDefRepository schemaDefRepository;
    private UserProfileRepository userProfileRepository;
    private List<SchemaDef> storedSchemaDefList;

    @Inject
    public DevelopmentInitializer(
            SchemaDefRepository schemaDefRepository,
            UserProfileRepository userProfileRepository) {

        this.schemaDefRepository = schemaDefRepository;
        this.userProfileRepository = userProfileRepository;

        this.storedSchemaDefList = this.schemaDefRepository.getAll();
        this.init();
    }
    
    private void init() {
        UserProfile adminProfile = new UserProfile();

        adminProfile.setName("AdminProfile");

        List<SchemaBuilder> schemaBuilders = Arrays.asList(
                new HeroSchemaBuilder(),
                new StudentSchemaBuilder(),
                new LibrarySchemaBuilder(),
                new FootballSchemaBuilder(),
                new BasketballSchemaBuilder(),
                new StackExchangeSchemaBuilder()
        );

        List<SchemaDef> schemaDefList = schemaBuilders.parallelStream()
                .filter(x -> !x.schemaExist(this.storedSchemaDefList))
                .map(SchemaBuilder::getSchemaDef)
                .collect(Collectors.toList());

        schemaDefList.parallelStream()
                .forEach(this.schemaDefRepository::save);

        this.userProfileRepository.save(adminProfile);
    }
}

