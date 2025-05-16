package com.red.stevo.chemsales.models;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageData {

    @JsonSetter(nulls = Nulls.SKIP)
    private Integer size = 10;

    @JsonSetter(nulls = Nulls.SKIP)
    private Integer page = 1;

    @Nullable()
    private String filter;

}
