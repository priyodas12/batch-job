package io.learn.batchjob.util;

import io.learn.batchjob.model.DiscountedProduct;
import io.learn.batchjob.model.Product;
import java.util.Random;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.apache.commons.lang3.math.NumberUtils;

@Slf4j
public class CustomProductProcessor implements ItemProcessor<Product, DiscountedProduct> {

  @Override
  public DiscountedProduct process(Product product) throws Exception {
    DiscountedProduct discountedProduct=createDiscountedPrice(product);
    log.info("process complete:: "+discountedProduct);
    return discountedProduct;
  }

  private Double getDiscount(){
    return new Random().nextDouble(50.00);
  }

  private Double getDiscountedPrice(DiscountedProduct discountedProduct,Double discount){
    Double currentPrice=discountedProduct.getPrice();
    return currentPrice-(currentPrice*(discount/100));
  }

  private DiscountedProduct createDiscountedPrice(Product product){
    DiscountedProduct discountedProduct=new DiscountedProduct();
    discountedProduct.setBatchId(NumberUtils.toInt(product.getProductId()));
    discountedProduct.setProductBarcode(UUID.randomUUID());
    discountedProduct.setProductName(product.getProductName());
    discountedProduct.setProductDesc(product.getProductDesc());
    discountedProduct.setPrice(NumberUtils.toDouble(product.getPrice(),00.0000));
    Double discount=(getDiscount());
    discountedProduct.setDiscount(NumberUtils.toDouble(String.valueOf(discount),0.0000));
    discountedProduct.setDiscountedPrice(NumberUtils.toDouble(String.valueOf(getDiscountedPrice(discountedProduct,discount)),0.0000));
    return discountedProduct;
  }
}
