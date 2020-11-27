package io.functionx.http.cmc.cryptocurrency;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;


@Data
public class Platform {
    private int id;
    private String name;
    private String symbol;
    private String slug;
    @JSONField(name = "token_address")
    private String tokenAddress;
}