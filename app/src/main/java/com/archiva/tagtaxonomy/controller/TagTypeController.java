package com.archiva.tagtaxonomy.controller;

import com.archiva.tagtaxonomy.dto.CreateTagTypeRequest;
import com.archiva.tagtaxonomy.dto.TagTypeResponse;
import com.archiva.tagtaxonomy.dto.UpdateTagTypeRequest;
import com.archiva.tagtaxonomy.mapper.TagTypeMapper;
import com.archiva.tagtaxonomy.service.entity.TagType;
import com.archiva.tagtaxonomy.service.service.TagTypeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tag-types")
public class TagTypeController {

    private final TagTypeService tagTypeService;
    private final TagTypeMapper tagTypeMapper;

    public TagTypeController(TagTypeService tagTypeService, TagTypeMapper tagTypeMapper) {
        this.tagTypeService = tagTypeService;
        this.tagTypeMapper = tagTypeMapper;
    }

    @PostMapping
    public ResponseEntity<TagTypeResponse> create(@Valid @RequestBody CreateTagTypeRequest request) {
        TagType created = tagTypeService.create(tagTypeMapper.toEntity(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(tagTypeMapper.toResponse(created));
    }

    @GetMapping
    public ResponseEntity<List<TagTypeResponse>> list() {
        List<TagTypeResponse> response = tagTypeService.list().stream()
                .map(tagTypeMapper::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TagTypeResponse> update(@PathVariable UUID id, @Valid @RequestBody UpdateTagTypeRequest request) {
        TagType updated = tagTypeService.update(id, tagTypeMapper.toEntity(request), request.version());
        return ResponseEntity.ok(tagTypeMapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        tagTypeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
