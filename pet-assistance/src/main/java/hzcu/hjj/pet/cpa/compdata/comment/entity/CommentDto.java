package hzcu.hjj.pet.cpa.compdata.comment.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class CommentDto {
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
    /**
     * 评论人头像
     */
    private String userPhotoUrl;
    /**
     * 评论人昵称
     */
    private String userAppName;
}
