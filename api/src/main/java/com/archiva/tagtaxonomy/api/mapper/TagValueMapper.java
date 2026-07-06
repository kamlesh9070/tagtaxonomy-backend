package com.archiva.tagtaxonomy.api.mapper;

import com.archiva.tagtaxonomy.api.dto.CreateTagValueRequest;
import com.archiva.tagtaxonomy.api.dto.TagValueResponse;
import com.archiva.tagtaxonomy.api.dto.TagValueTreeResponse;
import com.archiva.tagtaxonomy.api.dto.UpdateTagValueRequest;
import com.archiva.tagtaxonomy.service.entity.TagValue;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TagValueMapper {

    @Mapping(target = "tagTypeId", source = "tagType.publicId")
    @Mapping(target = "parentId", source = "parent.publicId")
    TagValueResponse toResponse(TagValue entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "publicId", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "modifiedDate", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "tagType", ignore = true)
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "children", ignore = true)
    TagValue toEntity(CreateTagValueRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "publicId", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "modifiedDate", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "tagType", ignore = true)
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "children", ignore = true)
    TagValue toEntity(UpdateTagValueRequest request);

    default TagValueTreeResponse toTreeResponse(TagValue entity) {
        return new TagValueTreeResponse(
                entity.getPublicId(),
                entity.getName(),
                entity.getDescription(),
                entity.getTagType().getPublicId(),
                entity.getParent() != null ? entity.getParent().getPublicId() : null,
                entity.getVersion(),
                entity.getChildren().stream().map(this::toTreeResponse).toList()
        );
    }
}
