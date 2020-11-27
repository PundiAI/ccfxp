package io.functionx.websocket.bithumb.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class BithumbMessage implements Serializable {


    private int code;
    private Data data;
    private long timestamp;
    private String topic;

    @lombok.Data
    public class Data implements Serializable {

        private List<String[]> b;
        private List<String[]> s;
        private String symbol;
        private String ver;

    }

}
