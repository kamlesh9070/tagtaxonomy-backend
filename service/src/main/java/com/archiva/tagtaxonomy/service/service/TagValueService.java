package com.archiva.tagtaxonomy.service.service;

import com.archiva.tagtaxonomy.service.entity.TagType;
import com.archiva.tagtaxonomy.service.entity.TagValue;
import com.archiva.tagtaxonomy.service.exception.ConflictException;
import com.archiva.tagtaxonomy.service.exception.OptimisticLockException;
import com.archiva.tagtaxonomy.service.exception.ResourceNotFoundException;
import com.archiva.tagtaxonomy.service.repository.TagTypeRepository;
import com.archiva.tagtaxonomy.service.repository.TagValueRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class TagValueService {

    private final TagValueRepository tagValueRepository;
    private final TagTypeRepository tagTypeRepository;

    public TagValueService(TagValueRepository tagValueRepository, TagTypeRepository tagTypeRepository) {
        this.tagValueRepository = tagValueRepository;
        this.tagTypeRepository = tagTypeRepository;
    }

    @Transactional
    public TagValue create(UUID tagTypeId, UUID parentId, TagValue tagValue) {
        TagType tagType = tagTypeRepository.findByPublicId(tagTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("Tag type not found"));
        tagValue.setTagType(tagType);
        validate(tagValue);
        if (parentId != null) {
            TagValue parent = resolveParent(parentId);
            ensureSameTagType(parent, tagType);
            validateDepth(parent);
            tagValue.setParent(parent);
        }
        return tagValueRepository.save(tagValue);
    }

    @Transactional
    public TagValue update(UUID publicId, UUID tagTypeId, UUID parentId, TagValue updatedValue, Long version) {
        TagValue existing = getByPublicId(publicId);
        ensureVersion(existing, version);
        validate(updatedValue);
        TagType tagType = tagTypeRepository.findByPublicId(tagTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("Tag type not found"));
        if (!existing.getTagType().getPublicId().equals(tagType.getPublicId())) {
            throw new ConflictException("Tag type is immutable after creation");
        }
        existing.setName(updatedValue.getName().trim());
        existing.setDescription(updatedValue.getDescription());
        if (parentId != null) {
            TagValue parent = resolveParent(parentId);
            ensureSameTagType(parent, tagType);
            ensureNotSelf(parent, publicId);
            ensureNotDescendant(parent, existing);
            validateDepth(parent);
            existing.setParent(parent);
        } else {
            existing.setParent(null);
        }
        return tagValueRepository.save(existing);
    }

    @Transactional
    public void delete(UUID publicId) {
        TagValue existing = getByPublicId(publicId);
        tagValueRepository.delete(existing);
    }

    public TagValue getByPublicId(UUID publicId) {
        return tagValueRepository.findByPublicId(publicId)
                .orElseThrow(() -> new ResourceNotFoundException("Tag value not found"));
    }

    public List<TagValue> getTree(UUID tagTypeId) {
        TagType tagType = tagTypeRepository.findByPublicId(tagTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("Tag type not found"));
        return tagValueRepository.findByTagTypeIdAndParentIsNull(tagType.getId());
    }

    private TagValue resolveParent(UUID parentId) {
        return tagValueRepository.findByPublicId(parentId)
                .orElseThrow(() -> new ConflictException("Parent tag value not found"));
    }

    private void ensureSameTagType(TagValue parent, TagType tagType) {
        if (!parent.getTagType().getPublicId().equals(tagType.getPublicId())) {
            throw new ConflictException("Parent tag value must belong to the same tag type");
        }
    }

    private void ensureNotSelf(TagValue parent, UUID publicId) {
        if (parent.getPublicId().equals(publicId)) {
            throw new ConflictException("Tag value cannot be its own parent");
        }
    }

    private void validateDepth(TagValue parent) {
        int depth = 1;
        TagValue cursor = parent;
        while (cursor.getParent() != null) {
            depth++;
            cursor = cursor.getParent();
        }
        if (depth >= 5) {
            throw new ConflictException("Maximum tag value depth is 5");
        }
    }

    private void ensureNotDescendant(TagValue parent, TagValue existing) {
        TagValue cursor = parent;
        while (cursor != null) {
            if (cursor.getPublicId().equals(existing.getPublicId())) {
                throw new ConflictException("Tag value cannot be moved under its own descendant");
            }
            cursor = cursor.getParent();
        }
    }

    private void validate(TagValue tagValue) {
        if (tagValue == null) {
            throw new IllegalArgumentException("Tag value is required");
        }
        if (tagValue.getName() == null || tagValue.getName().isBlank()) {
            throw new IllegalArgumentException("Tag value name is required");
        }
        if (tagValue.getName().length() > 100) {
            throw new IllegalArgumentException("Tag value name must be at most 100 characters");
        }
        if (tagValue.getDescription() instanceof String description && description.length() > 500) {
            throw new IllegalArgumentException("Tag value description must be at most 500 characters");
        }
    }

    private void ensureVersion(TagValue existing, Long version) {
        if (version == null || !version.equals(existing.getVersion())) {
            throw new OptimisticLockException("The tag value has been modified by another request");
        }
    }
}
