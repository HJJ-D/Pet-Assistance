package hzcu.hjj.pet.cpa.compdata.collect.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@TableName(value = "pet_assistance_collect", excludeProperty = "isNew")
public class Collect implements Serializable {
    /**
     * 收藏ID
     */
    @TableId
    private String collectId;
    /**
     * 收藏用户ID
     */
    private String petAssistanceUserId;
    /**
     * 收藏帖子ID
     */
    private String petAssistanceHelpId;
    /**
     * 是否删除  T:删除 不喜欢  F：没删除 喜欢
     */
    private Boolean collectDeleted;
}
