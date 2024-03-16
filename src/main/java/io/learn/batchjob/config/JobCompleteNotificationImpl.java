package io.learn.batchjob.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JobCompleteNotificationImpl implements JobExecutionListener {

  @Override
  public void beforeJob(JobExecution jobExecution) {
    JobExecutionListener.super.beforeJob(jobExecution);
    log.info("Job Starts");
  }

  @Override
  public void afterJob(JobExecution jobExecution) {
    JobExecutionListener.super.afterJob(jobExecution);
    if(jobExecution.getStatus()== BatchStatus.STOPPED) {
      log.info("Job Completed");
    }
  }
}
