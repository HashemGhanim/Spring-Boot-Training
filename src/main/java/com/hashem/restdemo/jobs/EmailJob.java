package com.hashem.restdemo.jobs;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Slf4j
@Component
public class EmailJob extends QuartzJobBean {

    @Autowired
    private MailProperties mailProperties;

    @Autowired
    private JavaMailSender sender;


    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobDataMap = context.getMergedJobDataMap();

        List<String> emails = (List<String>) jobDataMap.get("emails");
        List<String> names = (List<String>) jobDataMap.get("studentNames");
        String courseName = (String) jobDataMap.get("lesson");


        for(int i = 0 ; i < Math.min(emails.size() , names.size()); i++){
            try {
                sendMail(mailProperties.getUsername() , emails.get(i) , courseName , names.get(i));
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }

    }


    private void sendMail(String from , String to , String lesson , String name) throws MessagingException {

        String email = "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "    <meta charset=\"UTF-8\">" +
                "    <title>Lesson Reminder</title>" +
                "    <style>" +
                "        /* Styles for the email content */" +
                "        body {" +
                "            font-family: Arial, sans-serif;" +
                "            line-height: 1.6;" +
                "            margin: 0;" +
                "            padding: 0;" +
                "            background-color: #f5f5f5;" +
                "        }" +
                "        .container {" +
                "            max-width: 600px;" +
                "            margin: 0 auto;" +
                "            padding: 20px;" +
                "            background-color: #fff;" +
                "        }" +
                "        .header {" +
                "            text-align: center;" +
                "            background-color: #007bff;" +
                "            color: #fff;" +
                "            padding: 10px;" +
                "        }" +
                "        .lesson-details {" +
                "            padding: 10px;" +
                "        }" +
                "        .lesson-info {" +
                "            font-size: 18px;" +
                "            font-weight: bold;" +
                "            margin-bottom: 10px;" +
                "        }" +
                "        .lesson-date {" +
                "            font-size: 14px;" +
                "            color: #666;" +
                "        }" +
                "        .action-button {" +
                "            display: inline-block;" +
                "            background-color: #007bff;" +
                "            color: #fff;" +
                "            text-decoration: none;" +
                "            padding: 10px 20px;" +
                "            margin-top: 20px;" +
                "            border-radius: 4px;" +
                "        }" +
                "        .footer {" +
                "            text-align: center;" +
                "            background-color: #007bff;" +
                "            color: #fff;" +
                "            padding: 10px;" +
                "        }" +
                "        span{ color:white;}"+
                "    </style>" +
                "</head>" +
                "<body>" +
                "    <div class=\"container\">" +
                "        <div class=\"header\">" +
                "            <h1>Lesson Reminder</h1>" +
                "        </div>" +
                "        <div class=\"lesson-details\">" +
                "            <p class=\"lesson-info\">Hello "+name+",</p>" +
                "            <p>You have a lesson scheduled for the following details:</p>" +
                "            <p class=\"lesson-info\">Lesson: "+lesson+"</p>" +
                "            <p class=\"lesson-date\">Date: "+ ZonedDateTime.now().plusMinutes(15).toString() + "</p>" +
                "            <p>Please make sure to attend the lesson on time. If you have any questions or need to reschedule, feel free to contact us.</p>" +
                "        </div>" +
                "        <div class=\"footer\">" +
                "            <p>For any inquiries, please contact us at <span>hashemzerei45@gmail.com</span> .</p>" +
                "        </div>" +
                "    </div>" +
                "</body>" +
                "</html>";
        MimeMessage message = sender.createMimeMessage();

        MimeMessageHelper messageHelper = new MimeMessageHelper(message , StandardCharsets.UTF_8.toString());

        messageHelper.setFrom(from);
        messageHelper.setTo(to);
        messageHelper.setSubject("The lesson has begun...!");
        messageHelper.setText(email , true);

        sender.send(message);
    }
}
