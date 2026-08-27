package com.metesevim.staj2026.document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document
public class CouchbaseProductDocument {

    @Id
    private String id;

    @Field
    private String name;

    @Field
    private BigDecimal price;

    @Field
    private Integer stock;

    @Field
    private Boolean active;
}
