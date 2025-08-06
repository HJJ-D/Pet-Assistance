package hzcu.hjj.pet.cpa.compdata.assistance.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import hzcu.hjj.pet.cpa.compdata.comment.entity.CommentDto;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@JsonSerialize
@Data
public class UserUniteAssistanceDto implements Serializable {

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
    private LocalDateTime helpPublishTime;
    /**
     * 上门喂养时间 上门喂养时间
     */
    private LocalDateTime helpBeginTime;
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
     * APP内用户名
     */
    private String userAppName;
    /**
     * 用户头像
     */
    private String userPhotoUrl;
    /**
     * 用户手机号
     */
    private String userPhoneNum;
    /**
     * 用户省份
     */
    private String userProvince;
    /**
     * 用户地级市
     */
    private String userCity;
    /**
     * 用户所在地区
     */
    private String userArea;
    /**
     * 建号时间 建号时间 xx/xx/xx string 格式
     */
    private String createDate;
    /**
     * 用户性别
     */
    private Integer userGender;
    /**
     * 用户简介
     */
    private String userIntroduction;
    /**
     * 是否帮助
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
    /**
     * 是否删除  T:删除 不喜欢  F：没删除 喜欢
     */
    private Boolean likeDeleted;
    /**
     * 是否删除  T:删除 不喜欢  F：没删除 喜欢
     */
    private Boolean collectDeleted;
    /**
     * 点赞ID
     */
    private String likeId;
    /**
     * 收藏ID
     */
    private String collectId;
    /**
     * 评论数组
     */
    private List<CommentDto> comment;
}
