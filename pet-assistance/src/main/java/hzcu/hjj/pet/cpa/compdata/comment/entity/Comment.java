package hzcu.hjj.pet.cpa.compdata.comment.entity;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 喂养帖子评论表
 *
 * @author hujj
 * @date 2024-04-15
 */
@Data
@NoArgsConstructor
@TableName(value = "pet_assistance_comment", excludeProperty = "isNew")
public class Comment implements Serializable {

    /**
     * 评论ID
     */
    @TableId
    private String commentId;
    /**
     * 帖子ID
     */
    private String petAssistanceHelpId;
    /**
     * 评论人ID
     */
    private String petAssistanceUserId;
    /**
     * 评论内容
     */
    private String commentContent;
    /**
     * 评论时间
     */
    private Date commentDate;

}
