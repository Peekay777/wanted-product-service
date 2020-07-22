package com.koutsios.wantedproductservice.dto;

import com.koutsios.wantedproductservice.constant.ProductStatus;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewWantedProduct {

  @NotBlank(message = "Product must have name")
  private String name;

  @URL(message = "Invalid link to product details")
  private String productDetailsLink;

  @NotBlank(message = "Product must have a valid status")
  private ProductStatus status;

}
