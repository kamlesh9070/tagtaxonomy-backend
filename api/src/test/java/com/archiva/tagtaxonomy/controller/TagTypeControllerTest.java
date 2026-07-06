package com.archiva.tagtaxonomy.controller;

import com.archiva.tagtaxonomy.api.dto.CreateTagTypeRequest;
import com.archiva.tagtaxonomy.api.dto.TagTypeResponse;
import com.archiva.tagtaxonomy.api.exception.GlobalExceptionHandler;
import com.archiva.tagtaxonomy.api.mapper.TagTypeMapper;
import com.archiva.tagtaxonomy.service.entity.TagType;
import com.archiva.tagtaxonomy.service.service.TagTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TagTypeControllerTest {

    @Mock
    private TagTypeService tagTypeService;

    @Mock
    private TagTypeMapper tagTypeMapper;

    @InjectMocks
    private TagTypeController tagTypeController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(tagTypeController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void createTagTypeReturnsCreated() throws Exception {
        CreateTagTypeRequest request = new CreateTagTypeRequest("Finance", "Finance taxonomy", true, false);
        TagType entity = new TagType();
        entity.setName("Finance");
        entity.setDescription("Finance taxonomy");
        entity.setMultiValue(true);
        entity.setRequired(false);
        entity.setPublicId(UUID.randomUUID());
        entity.setVersion(0L);

        when(tagTypeMapper.toEntity(request)).thenReturn(entity);
        when(tagTypeService.create(entity)).thenReturn(entity);
        when(tagTypeMapper.toResponse(entity)).thenReturn(new TagTypeResponse(entity.getPublicId(), entity.getName(), entity.getDescription(), entity.isMultiValue(), entity.isRequired(), entity.getVersion()));

        mockMvc.perform(post("/api/tag-types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Finance\",\"description\":\"Finance taxonomy\",\"isMultiValue\":true,\"isRequired\":false}"))
                .andExpect(status().isCreated());
    }
}
