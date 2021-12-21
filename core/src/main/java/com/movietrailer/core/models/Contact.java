package com.movietrailer.core.models;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Contact {

    private String firstName;

    private String lastName;

    private String mobile;

    private String movieTrailerName;

}
