package com.fivecafe.websocket;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/salary")
public class SalaryWSEndpoint {
    private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());
            
    @OnMessage
    public String onMessage(Session session, String message) {
        System.out.println("WEBSOCKET INFO: /salary:onMessage -> message: " + message);
        for (Session s : sessions) {
            try {
                s.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    @OnOpen
    public void onOpen(Session session) {
        System.out.println("WEBSOCKET INFO: /salary:onOpen -> id: " + session.getId());
        sessions.add(session);
    }
    
    @OnClose
    public void onClose(Session session) {
        System.out.println("WEBSOCKET INFO: /salary:onClose -> id: " + session.getId());
        sessions.remove(session);
    }
}
