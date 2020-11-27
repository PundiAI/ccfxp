package io.functionx.websocket;


public interface IExchangeWebSocketService {


    void onStart();


    void onSubTopics();


    void onReceive(String msg);


    void online();


    void offline();

}
