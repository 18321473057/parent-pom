package org.line.core.sk.dto;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    private String key;
    private String value;

    @Override
    public String toString() {
        return key + "=" + value;
    }

}