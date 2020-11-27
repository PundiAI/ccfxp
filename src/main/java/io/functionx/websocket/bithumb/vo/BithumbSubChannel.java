package io.functionx.websocket.bithumb.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BithumbSubChannel implements Serializable {

    private String cmd = "subscribe";

    private List<String> args;

}
