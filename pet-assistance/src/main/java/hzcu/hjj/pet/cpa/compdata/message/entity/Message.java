package hzcu.hjj.pet.cpa.compdata.message.entity;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 消息表实体类
 *
 * @author hujj
 * @date 2024-03-28
 */
@Data
@NoArgsConstructor
@TableName(value = "pet_assistance_message", excludeProperty = "isNew")
public class Message implements Serializable {

    /**
     * 消息ID
     */
    @TableId
    private String petAssistanceMessageId;
    /**
     * 消息时间
     */
    private Date messageDate;
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
}
