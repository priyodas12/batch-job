package io.learn.batchjob.model;


import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DiscountedProduct{

  private Integer batchId;
  private UUID productBarcode;
  private String productName;
  private String productDesc;
  private Double price;
  private Double discount;
  private Double discountedPrice;

}
