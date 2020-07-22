package com.koutsios.wantedproductservice.domain;

import static lombok.AccessLevel.PRIVATE;

import com.koutsios.wantedproductservice.constant.ProductStatus;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "wantedProduct")
public class WantedProduct {

  @Id
  @Setter(PRIVATE)
  private String id;

  @NotBlank(message = "Product must have wishlist Id")
  private String wishlistId;

  @NotBlank(message = "Product must have name")
  private String name;

  @URL(message = "Invalid link to product details")
  private String productDetailsLink;

  @NotBlank(message = "Product must have a valid status")
  private ProductStatus status;

}
