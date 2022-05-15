package com.bibliotheque.model.dto.googleBook;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class VolumeInfo {
    public String title;
    public String subtitle;
    public String[] authors;
    public String publisher;
    public String publishedDate;
    public String description;
    public IndustryIdentifier[] industryIdentifiers;
    public int pageCount;
    public String printType;
    public String[] categories;
    public String maturityRating;
    public boolean allowAnonLogging;
    public String contentVersion;
    public ImageLinks imageLinks;
    public String language;
    public String previewLink;
    public String infoLink;
    public String canonicalVolumeLink;
}