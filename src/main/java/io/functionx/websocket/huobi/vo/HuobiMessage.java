package io.functionx.websocket.huobi.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class HuobiMessage implements Serializable {

    private String ch;
    private Long ts;
    private Tick tick;


    @Data
    private class Tick implements Serializable {

        private Long seqNum;
        private Long prevSeqNum;
        private List<String[]> bids;
        private List<String[]> asks;

    }

}
