package io.functionx.websocket.binance.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubChannel implements Serializable {

    private String method = "SUBSCRIBE";

    private List<String> params;

    private Integer id;

}
