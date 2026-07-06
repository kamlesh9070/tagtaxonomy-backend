package com.archiva.tagtaxonomy.service.service;

import com.archiva.tagtaxonomy.service.entity.TagType;
import com.archiva.tagtaxonomy.service.entity.TagValue;
import com.archiva.tagtaxonomy.service.exception.ResourceNotFoundException;
import com.archiva.tagtaxonomy.service.repository.TagTypeRepository;
import com.archiva.tagtaxonomy.service.repository.TagValueRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TagValueServiceTest {

    @Mock
    private TagValueRepository tagValueRepository;

    @Mock
    private TagTypeRepository tagTypeRepository;

    @InjectMocks
    private TagValueService tagValueService;

    @Test
    void createFailsWhenTagTypeDoesNotExist() {
        UUID tagTypeId = UUID.randomUUID();
        TagValue incoming = new TagValue();
        incoming.setName("Alpha");
        incoming.setTagType(new TagType());

        when(tagTypeRepository.findByPublicId(tagTypeId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> tagValueService.create(tagTypeId, null, incoming));
    }
}
