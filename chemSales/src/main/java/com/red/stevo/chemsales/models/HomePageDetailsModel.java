package com.red.stevo.chemsales.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HomePageDetailsModel {

    private Integer totalPages;

    private Integer totalCount;

    private List<HomeRowDataModel> homeRowDataList;

}
