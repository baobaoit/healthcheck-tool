package com.ewm.card.ewmhealthchecktool.config;

import com.ewm.card.ewmhealthchecktool.command.ServiceHealthCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import picocli.CommandLine;
import picocli.CommandLine.IFactory;

@Configuration
@EnableScheduling
public class ScheduleConfiguration {
    private ApplicationArguments applicationArguments;
    private IFactory factory;
    private ServiceHealthCheck serviceHealthCheck;

    @Scheduled(cron = "${schedule.cron-expression.check-services-health}")
    public void checkServicesHealth() {
        CommandLine commandLine = new CommandLine(serviceHealthCheck, factory);
        commandLine.execute(applicationArguments.getSourceArgs());
    }

    @Autowired
    public void setApplicationArguments(ApplicationArguments applicationArguments) {
        this.applicationArguments = applicationArguments;
    }

    @Autowired
    public void setFactory(IFactory factory) {
        this.factory = factory;
    }

    @Autowired
    public void setServiceHealthCheck(ServiceHealthCheck serviceHealthCheck) {
        this.serviceHealthCheck = serviceHealthCheck;
    }
}
