package hzcu.hjj.pet.cpa.compdata.like.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@TableName(value = "pet_assistance_like", excludeProperty = "isNew")
public class Like implements Serializable {
    /**
     * 点赞ID
     */
    @TableId
    private String likeId;
    /**
     * 点赞用户ID
     */
    private String petAssistanceUserId;
    /**
     * 点赞帖子ID
     */
    private String petAssistanceHelpId;
    /**
     * 是否删除  T:删除 不喜欢  F：没删除 喜欢
     */
    private Boolean likeDeleted;
}
