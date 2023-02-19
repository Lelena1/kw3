package me.luppolem.socksapp.model;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Socks {
    @NotEmpty(message = "Color is mandatory")
    private Color color;
    @NotEmpty(message = "Size is mandatory")
    private Size size;
    @Positive
    private int cottonPart;

}
