package io.functionx.websocket.binance.vo;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class BinanceMessage implements Serializable {

    private String s;
    @JSONField(name = "E")
    private String dt;
    private List<String[]> b;
    private List<String[]> a;

}
