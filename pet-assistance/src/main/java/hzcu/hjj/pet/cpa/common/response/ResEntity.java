package hzcu.hjj.pet.cpa.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"status", "message", "data"})
public class ResEntity<T> {

    /**
     * HTTP 状态码
     */
    @JsonProperty("status")
    private int statusCode;

    /**
     * 状态描述或错误消息
     */
    @JsonProperty("message")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    /**
     * 响应数据
     */
    @JsonProperty("data")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    /**
     * 创建成功的响应实体
     *
     * @param data 响应数据
     * @return ResponseEntity实例
     */
    public static <T> ResEntity<T> ok(T data) {
        return ResEntity.<T>builder()
                .statusCode(200)
                .message("OK")
                .data(data)
                .build();
    }

    /**
     * 创建失败的响应实体（默认状态码：400，错误消息：Bad Request）
     *
     * @param errorMessage 错误消息
     * @return ResponseEntity实例
     */
    public static ResEntity<Void> badRequest(String errorMessage) {
        return ResEntity.<Void>builder()
                .statusCode(400)
                .message(errorMessage)
                .build();
    }

}
