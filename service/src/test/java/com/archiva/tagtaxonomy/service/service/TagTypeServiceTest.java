package com.archiva.tagtaxonomy.service.service;

import com.archiva.tagtaxonomy.service.entity.TagType;
import com.archiva.tagtaxonomy.service.exception.ConflictException;
import com.archiva.tagtaxonomy.service.repository.TagTypeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TagTypeServiceTest {

    @Mock
    private TagTypeRepository tagTypeRepository;

    @InjectMocks
    private TagTypeService tagTypeService;

    @Test
    void createRejectsDuplicateNamesIgnoringCase() {
        TagType incoming = new TagType();
        incoming.setName("Finance");

        when(tagTypeRepository.existsByNameIgnoreCase(anyString())).thenReturn(true);

        assertThrows(ConflictException.class, () -> tagTypeService.create(incoming));
        verify(tagTypeRepository, never()).save(org.mockito.ArgumentMatchers.any());
    }
}
