package io.functionx.http.cmc;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;


@Data
public class ApiResponse<T> {
    private Status status;
    private T data;

    public static class Status {
        private Date timestamp;
        @JSONField(name = "error_code")
        private int errorCode;
        @JSONField(name = "error_message")
        private String errorMessage;
        private int elapsed;
        @JSONField(name = "credit_count")
        private int creditCount;

    }
}