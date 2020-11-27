package io.functionx.websocket.huobi.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HuobiSubChannel implements Serializable {

    private String sub;
    private String id;
    private Long from;
    private Long to;

}
