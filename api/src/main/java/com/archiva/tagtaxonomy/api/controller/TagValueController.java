package com.archiva.tagtaxonomy.api.controller;

import com.archiva.tagtaxonomy.api.dto.CreateTagValueRequest;
import com.archiva.tagtaxonomy.api.dto.TagValueResponse;
import com.archiva.tagtaxonomy.api.dto.TagValueTreeResponse;
import com.archiva.tagtaxonomy.api.dto.UpdateTagValueRequest;
import com.archiva.tagtaxonomy.api.mapper.TagValueMapper;
import com.archiva.tagtaxonomy.service.entity.TagValue;
import com.archiva.tagtaxonomy.service.service.TagValueService;
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
@RequestMapping("/api/tag-values")
public class TagValueController {

    private final TagValueService tagValueService;
    private final TagValueMapper tagValueMapper;

    public TagValueController(TagValueService tagValueService, TagValueMapper tagValueMapper) {
        this.tagValueService = tagValueService;
        this.tagValueMapper = tagValueMapper;
    }

    @PostMapping
    public ResponseEntity<TagValueResponse> create(@Valid @RequestBody CreateTagValueRequest request) {
        TagValue created = tagValueService.create(request.tagTypeId(), request.parentId(), tagValueMapper.toEntity(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(tagValueMapper.toResponse(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TagValueResponse> update(@PathVariable UUID id, @Valid @RequestBody UpdateTagValueRequest request) {
        TagValue updated = tagValueService.update(id, request.tagTypeId(), request.parentId(), tagValueMapper.toEntity(request), request.version());
        return ResponseEntity.ok(tagValueMapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        tagValueService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tree/{tagTypeId}")
    public ResponseEntity<List<TagValueTreeResponse>> getTree(@PathVariable UUID tagTypeId) {
        List<TagValueTreeResponse> response = tagValueService.getTree(tagTypeId).stream()
                .map(tagValueMapper::toTreeResponse)
                .toList();
        return ResponseEntity.ok(response);
    }
}
