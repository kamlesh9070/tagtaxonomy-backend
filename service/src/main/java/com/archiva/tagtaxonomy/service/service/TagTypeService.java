package com.archiva.tagtaxonomy.service.service;

import com.archiva.tagtaxonomy.service.entity.TagType;
import com.archiva.tagtaxonomy.service.exception.ConflictException;
import com.archiva.tagtaxonomy.service.exception.OptimisticLockException;
import com.archiva.tagtaxonomy.service.exception.ResourceNotFoundException;
import com.archiva.tagtaxonomy.service.repository.TagTypeRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class TagTypeService {

    private final TagTypeRepository tagTypeRepository;

    public TagTypeService(TagTypeRepository tagTypeRepository) {
        this.tagTypeRepository = tagTypeRepository;
    }

    @Transactional
    public TagType create(TagType tagType) {
        validate(tagType);
        String normalized = normalizeName(tagType.getName());
        if (tagTypeRepository.existsByNameIgnoreCase(normalized)) {
            throw new ConflictException("Tag type name already exists");
        }
        tagType.setName(normalized);
        return tagTypeRepository.save(tagType);
    }

    public List<TagType> list() {
        return tagTypeRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    public TagType getByPublicId(UUID publicId) {
        return tagTypeRepository.findByPublicId(publicId)
                .orElseThrow(() -> new ResourceNotFoundException("Tag type not found"));
    }

    @Transactional
    public TagType update(UUID publicId, TagType updatedTagType, Long version) {
        TagType existing = getByPublicId(publicId);
        ensureVersion(existing, version);
        validate(updatedTagType);
        String normalized = normalizeName(updatedTagType.getName());
        tagTypeRepository.findByNameIgnoreCase(normalized)
                .filter(candidate -> !candidate.getPublicId().equals(publicId))
                .ifPresent(candidate -> {
                    throw new ConflictException("Tag type name already exists");
                });
        existing.setName(normalized);
        existing.setDescription(updatedTagType.getDescription());
        existing.setMultiValue(updatedTagType.isMultiValue());
        existing.setRequired(updatedTagType.isRequired());
        return tagTypeRepository.save(existing);
    }

    @Transactional
    public void delete(UUID publicId) {
        TagType existing = getByPublicId(publicId);
        tagTypeRepository.delete(existing);
    }

    private void validate(TagType tagType) {
        if (tagType == null) {
            throw new IllegalArgumentException("Tag type is required");
        }
        String name = normalizeName(tagType.getName());
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Tag type name is required");
        }
        if (name.length() > 100) {
            throw new IllegalArgumentException("Tag type name must be at most 100 characters");
        }
        String description = tagType.getDescription();
        if (description != null && description.length() > 500) {
            throw new IllegalArgumentException("Tag type description must be at most 500 characters");
        }
    }

    private String normalizeName(String value) {
        return value == null ? null : value.trim();
    }

    private void ensureVersion(TagType existing, Long version) {
        if (version == null || !version.equals(existing.getVersion())) {
            throw new OptimisticLockException("The tag type has been modified by another request");
        }
    }
}
