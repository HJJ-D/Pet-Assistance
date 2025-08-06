package hzcu.hjj.pet.cpa.compdata.user.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TableName("pet_assistance_user")
public class User {

    /**
     * 用户ID
     */
    @TableId
    private String petAssistanceUserId;
    /**
     * APP内用户名
     */
    private String userAppName;
    /**
     * 用户头像
     */
    private String userPhotoUrl;
    /**
     * 用户密码
     */
    private String userPwd;
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

}