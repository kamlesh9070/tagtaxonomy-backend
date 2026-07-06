package com.archiva.tagtaxonomy.service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tag_type")
public class TagType extends AuditableEntity {

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private boolean isMultiValue;

    @Column(nullable = false)
    private boolean isRequired;

    @OneToMany(mappedBy = "tagType")
    private List<TagValue> values = new ArrayList<>();
}
