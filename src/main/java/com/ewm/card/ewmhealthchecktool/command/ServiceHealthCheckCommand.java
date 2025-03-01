package com.ewm.card.ewmhealthchecktool.command;

import com.ewm.card.ewmhealthchecktool.constant.mail.EmailSubjectConstants;
import com.ewm.card.ewmhealthchecktool.constant.mail.EmailTemplateNameConstants;
import com.ewm.card.ewmhealthchecktool.mail.EmailMessageHelper;
import com.ewm.card.ewmhealthchecktool.mail.core.SimpleEmailSender;
import com.ewm.card.ewmhealthchecktool.mail.model.EmailMessage;
import com.ewm.card.ewmhealthchecktool.utils.ServiceHealthUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.ewm.card.ewmhealthchecktool.constant.mail.EmailTemplatePropertyConstants.PORTS;
import static com.ewm.card.ewmhealthchecktool.utils.ServiceHealthStatus.DOWN;

@Service
@Command(name = "service-health-check-cli", mixinStandardHelpOptions = true)
public class ServiceHealthCheckCommand implements ServiceHealthCheck {

    private static final Logger log = LoggerFactory.getLogger(ServiceHealthCheckCommand.class);

    @Option(names = {"-p", "--ports"}, description = "The services port need to health check", required = true,
            split = ",")
    private Set<Integer> ports;

    @Option(names = "--to", description = "Email(s) of recipient(s)", required = true, split = ",")
    private Set<String> emailSenders;

    @Value("${email.noreply}")
    private String noreplyEmail;

    private SimpleEmailSender simpleEmailSender;

    @Override
    public Integer call() throws Exception {
        List<Integer> portsDown = ports.stream()
                .filter(p -> DOWN.equals(ServiceHealthUtils.getServiceStatus(p)))
                .peek(ServiceHealthUtils::getServiceIpAddress)
                .collect(Collectors.toList());

        if (!portsDown.isEmpty()) {
            EmailMessage emailMessage = EmailMessageHelper.builder()
                    .emailFrom(noreplyEmail)
                    .emailTo(emailSenders)
                    .subject(() -> EmailSubjectConstants.SERVICES_HEALTH_CHECK_NOTICE_SUBJECT)
                    .templateName(EmailTemplateNameConstants.SERVICES_HEALTH_CHECK_NOTICE)
                    .templateProperties(() -> {
                        Map<String, Object> properties = new HashMap<>();
                        properties.put(PORTS, portsDown);

                        return properties;
                    })
                    .build();

            try {
                simpleEmailSender.sendEmail(emailMessage);

                log.info("Sending services health check notification successfully!");
            } catch (MessagingException e) {
                log.error("Sending services health check notification failed because: {}", e.getMessage());
            }
        }

        return 0;
    }

    @Autowired
    public void setEmailSender(SimpleEmailSender simpleEmailSender) {
        this.simpleEmailSender = simpleEmailSender;
    }
}
