package hzcu.hjj.pet.cpa.compdata.friendship.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 朋友关系表实体类
 *
 * @author hujj
 * @date 2024-03-28
 */
@Data
@NoArgsConstructor
@TableName(value = "pet_assistance_friendship", excludeProperty = "isNew")
public class Friendship implements Serializable {

    @TableId
    private String petAssistanceFriendshipId;
    private String petAssistanceUserId;
    /**
     * 朋友ID
     */
    private String petAssistanceFriendId;
}
