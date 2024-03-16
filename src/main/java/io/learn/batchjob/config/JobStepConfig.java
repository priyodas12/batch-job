package io.learn.batchjob.config;


import io.learn.batchjob.model.DiscountedProduct;
import io.learn.batchjob.model.Product;
import io.learn.batchjob.util.CustomProductProcessor;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Slf4j
@Configuration
public class JobStepConfig {

  @Bean
  public Step createSteps(JobRepository jobRepository,
      DataSourceTransactionManager transactionManager,
      FlatFileItemReader<Product> reader,
      ItemProcessor<Product, DiscountedProduct> processor,
      ItemWriter<DiscountedProduct> productWriter) {
    return new StepBuilder("job-step", jobRepository)
        .<Product,DiscountedProduct>chunk(10, transactionManager)
        .allowStartIfComplete(true)
        .reader(reader)
        .processor(processor)
        .writer(productWriter)
        .build();
  }

    //read from csv file
    @Bean
    public FlatFileItemReader<Product> reader(){
      log.info("Creating custom reader");

      return new FlatFileItemReaderBuilder<Product>()
          .name("productData.csv-reader")
          .resource(new ClassPathResource("productData.csv"))
          .linesToSkip(1)
          .delimited()
          .names("productId","productName","productDesc","price")
          .targetType(Product.class)
          .build();
    }

    @Bean
    public ItemProcessor<Product, DiscountedProduct> processor(){
    log.info("Creating custom processor");
      return new CustomProductProcessor();
    }

    @Bean
    public ItemWriter<DiscountedProduct> writer(DataSource dataSource){
      log.info("Creating custom writer");
      return new JdbcBatchItemWriterBuilder<DiscountedProduct>()
          .sql("INSERT INTO products(batch_id,product_barcode,product_name,product_desc,price,discount,discounted_price) VALUES (:batchId,:productBarcode,:productName,:productDesc,:price,:discount,:discountedPrice)")
          .dataSource(dataSource)
          .beanMapped()
          .build();
    }

}
