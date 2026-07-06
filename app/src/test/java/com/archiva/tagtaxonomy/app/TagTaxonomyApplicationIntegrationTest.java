package com.archiva.tagtaxonomy.app;

import com.archiva.tagtaxonomy.service.entity.TagType;
import com.archiva.tagtaxonomy.service.service.TagTypeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        properties = {
                "spring.liquibase.enabled=true",
                "spring.liquibase.change-log=classpath:db/changelog/db.changelog-master.yaml",
                "spring.liquibase.default-schema=PUBLIC",
                "logging.level.org.springframework.boot.autoconfigure.liquibase=DEBUG",
                "logging.level.liquibase=DEBUG"
        }
)
@ActiveProfiles("test")
class TagTaxonomyApplicationIntegrationTest {

    @Autowired
    private TagTypeService tagTypeService;

    @Test
    void contextLoadsAndPersistsTagType() {
        TagType tagType = new TagType();
        tagType.setName("Finance");
        tagType.setDescription("Finance taxonomy");
        tagType.setMultiValue(true);
        tagType.setRequired(false);

        TagType saved = tagTypeService.create(tagType);

        assertThat(saved.getPublicId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("Finance");
    }
}
