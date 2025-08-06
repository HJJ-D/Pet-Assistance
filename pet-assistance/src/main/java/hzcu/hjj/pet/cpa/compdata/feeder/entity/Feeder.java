package hzcu.hjj.pet.cpa.compdata.feeder.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 喂养人表 喂养人表实体类
 *
 * @author hujj
 * @date 2024-03-28
 */
@Data
@NoArgsConstructor
@TableName(value = "pet_assistance_feeder", excludeProperty = "isNew")
public class Feeder implements Serializable {

    /**
     * 喂养人ID 喂养人ID
     */
    @TableId
    private String petAssistanceFeederId;
    /**
     * 用户ID 用户ID
     */
    private String petAssistanceUserId;
}
