package vn.edu.hutech.bai2.model;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class Checkout {

    @NotBlank
    @NotNull
    private String customerName;

    private String address;

    @Pattern(regexp = "^(0\\d{9,10})$", message = "Please provide a valid phone number")
    @Nullable
    private String phone;

    private String note;
}
