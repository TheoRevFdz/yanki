package com.nttdata.bootcamp.msyanki.web.model;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class YankiModel {
    @JsonIgnore
    private String id;
    @NotBlank
    private String typeDoc;
    @NotBlank
    private String nroDoc;
    @NotBlank
    private String nroPhone;
    @NotBlank
    private String imei;
    @NotBlank
    private String email;
    private String nroAccount;
}
