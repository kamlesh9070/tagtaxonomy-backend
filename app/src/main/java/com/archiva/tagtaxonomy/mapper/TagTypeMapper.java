package com.archiva.tagtaxonomy.mapper;

import com.archiva.tagtaxonomy.dto.CreateTagTypeRequest;
import com.archiva.tagtaxonomy.dto.TagTypeResponse;
import com.archiva.tagtaxonomy.dto.UpdateTagTypeRequest;
import com.archiva.tagtaxonomy.service.entity.TagType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TagTypeMapper {

    @Mapping(target = "id", source = "publicId")
    @Mapping(target = "isMultiValue", source = "multiValue")
    @Mapping(target = "isRequired", source = "required")
    TagTypeResponse toResponse(TagType entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "publicId", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "modifiedDate", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "values", ignore = true)
    @Mapping(target = "multiValue", source = "isMultiValue")
    @Mapping(target = "required", source = "isRequired")
    TagType toEntity(CreateTagTypeRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "publicId", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "modifiedDate", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "values", ignore = true)
    @Mapping(target = "multiValue", source = "isMultiValue")
    @Mapping(target = "required", source = "isRequired")
    TagType toEntity(UpdateTagTypeRequest request);
}
