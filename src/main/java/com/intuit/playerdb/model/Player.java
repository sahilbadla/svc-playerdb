package com.intuit.playerdb.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Getter
@Setter
@Entity
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Player {
    @Id
    @JsonProperty
    private String playerID;
    @JsonProperty
    private int birthYear;
    @JsonProperty
    private int birthMonth;
    @JsonProperty
    private int birthDay;
    @JsonProperty
    private String birthCountry;
    @JsonProperty
    private String birthState;
    @JsonProperty
    private String birthCity;
    @JsonProperty
    @Nullable
    private int deathYear;
    @JsonProperty
    private int deathMonth;
    @JsonProperty
    private int deathDay;
    @JsonProperty
    private String deathCountry;
    @JsonProperty
    private String deathState;
    @JsonProperty
    private String deathCity;
    @JsonProperty
    private String nameFirst;
    @JsonProperty
    private String nameLast;
    @JsonProperty
    private String nameGiven;
    @JsonProperty
    private int weight;
    @JsonProperty
    private int height;
    @JsonProperty
    private String bats;
    @JsonProperty
    private String playerThrows;
    @JsonProperty
    private String debut;
    @JsonProperty
    private String finalGame;
    @JsonProperty
    private String retroID;
    @JsonProperty
    private String bbrefID;
}
