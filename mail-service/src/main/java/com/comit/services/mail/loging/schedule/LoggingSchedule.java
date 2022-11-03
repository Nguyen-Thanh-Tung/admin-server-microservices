package com.comit.services.mail.loging.schedule;

import com.comit.services.mail.loging.constant.Const;
import com.comit.services.mail.loging.model.CommonLogger;
import com.comit.services.mail.loging.model.CustomMessage;
import com.comit.services.mail.loging.model.RequestSessionLog;
import com.comit.services.mail.loging.service.LoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.PriorityQueue;

@EnableScheduling
@Component
public class LoggingSchedule {
    @Autowired
    private LoggingService loggingService;

    @Scheduled(cron = "* * * * * *")
    public void writeLog() {
        Map<String, RequestSessionLog> sessionLogMap = loggingService.getSessionLogMap();
        PriorityQueue<String> completeRequest = loggingService.getCompleteRequest();
        while (completeRequest.size() > 0) {
            String requestID = completeRequest.poll();
            CommonLogger.info(new CustomMessage(sessionLogMap.get(requestID)));
            sessionLogMap.remove(requestID);
        }
        loggingService.setSessionLogMap(sessionLogMap);
        loggingService.setCompleteRequest(completeRequest);
    }

    @Scheduled(cron = "0 */10 * * * *")
    public void processIncompleteLog() {
        Map<String, RequestSessionLog> sessionLogMap = loggingService.getSessionLogMap();
        PriorityQueue<String> completeRequest = loggingService.getCompleteRequest();
        for (Map.Entry<String, RequestSessionLog> entry : sessionLogMap.entrySet()) {
            if (System.currentTimeMillis() - entry.getValue().getRequestTime().getTime() > Const.TEN_MINUTE) {
                completeRequest.add(entry.getKey());
            }
        }
        loggingService.setSessionLogMap(sessionLogMap);
        loggingService.setCompleteRequest(completeRequest);
    }

}
