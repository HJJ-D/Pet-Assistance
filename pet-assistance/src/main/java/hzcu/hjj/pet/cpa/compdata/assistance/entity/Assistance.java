package hzcu.hjj.pet.cpa.compdata.assistance.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 喂养帖子表 喂养帖子表实体类
 *
 * @author hujj
 * @date 2024-03-28
 */
@Data
@NoArgsConstructor
@TableName(value = "pet_assistance_help", excludeProperty = "isNew")
public class Assistance implements Serializable {

    /**
     * 喂养帖子ID
     */
    @TableId
    private String petAssistanceHelpId;
    /**
     * 发帖人ID
     */
    private String petAssistanceUserId;
    /**
     * 帖子内容
     */
    private String helpContent;
    /**
     * 发帖时间 发帖时间
     */
    private Date helpPublishTime;
    /**
     * 上门喂养时间 上门喂养时间
     */
    private Date helpBeginTime;
    /**
     * 喂养状态 1-正在寻找喂养人  2-已经找到喂养人 3-喂养时间已过（结束喂养）
     */
    private Integer helpStatus;
    /**
     * 求助帖子标题
     */
    private String helpTitle;
    /**
     * 求助帖子封面url
     */
    private String helpCover;
    /**
     * 求助帖子内容图片url
     */
    private String helpImage;
    /**
     * 求助地区
     */
    private String helpArea;
    /**
     * 是否为求助帖子
     */
    private Boolean isHelp;
    /**
     * 视频url
     */
    private String helpVideo;
    /**
     * 点赞数
     */
    private Integer likeNum;
    /**
     * 收藏数
     */
    private Integer collectNum;
}
