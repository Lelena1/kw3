package me.luppolem.socksapp.model;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Warehouse {

    @NotEmpty(message = "All fields are mandatory")
   private Socks socks;



}
