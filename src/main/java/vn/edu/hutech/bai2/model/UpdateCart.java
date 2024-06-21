package vn.edu.hutech.bai2.model;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.Data;

@Data
public class UpdateCart {
    @NotNull
    Long productId;
    @NotNull
    int quantity;
}
