package com.movietrailer.core.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImdbDataModel {

    @JsonProperty("items")
    List<MovieData> items;

    @JsonProperty("results")
    List<MovieData> results;

}

