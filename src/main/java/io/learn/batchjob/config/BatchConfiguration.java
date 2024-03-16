package io.learn.batchjob.config;


import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BatchConfiguration {


  @Bean
  public Job getJobConfig(JobRepository jobRepository,
      JobCompleteNotificationImpl jobCompleteNotification,
      Step steps) {

    return new JobBuilder("sb-job-1", jobRepository)
        .listener(jobCompleteNotification) //do some activity before creating job or after creating job
        .start(steps) //steps to start job
        .build();

  }

}
