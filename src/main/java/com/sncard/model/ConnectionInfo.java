package com.sncard.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Pavel Neizhmak
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConnectionInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String provider;
    private String displayName = "No Name";
    private boolean isConfigured;

}
