package hzcu.hjj.pet.cpa.compdata.message.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
public class MessageUserDto {
    /**
     * 消息ID
     */
    @TableId
    private String petAssistanceMessageId;
    /**
     * 消息时间
     */
    private LocalDateTime messageDate;
    /**
     * 消息发送人ID
     */
    private String fromUserId;
    /**
     * 消息接收人ID
     */
    private String toUserId;
    /**
     * 消息内容
     */
    private String messageContent;
    /**
     * 是否已读
     */
    private Boolean isRead;
    /**
     * 用户ID
     */
    private String petAssistanceUserId;
    /**
     * APP内用户名
     */
    private String userAppName;
    /**
     * 用户头像
     */
    private String userPhotoUrl;
}
