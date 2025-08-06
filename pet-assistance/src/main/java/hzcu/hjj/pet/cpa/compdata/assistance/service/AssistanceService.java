package hzcu.hjj.pet.cpa.compdata.assistance.service;

import com.baomidou.mybatisplus.extension.service.IService;
import hzcu.hjj.pet.cpa.compdata.assistance.dto.UserUniteAssistanceDto;
import hzcu.hjj.pet.cpa.compdata.assistance.entity.Assistance;

import java.util.List;

public interface AssistanceService extends IService<Assistance> {
    /**
     * 根据地区进行推荐
     * @param userArea
     * @return
     */
    public List<UserUniteAssistanceDto> recommendByArea(String userArea);
    /**
     * 获取单条信息
     */
    public List<UserUniteAssistanceDto> getAssistance(String petAssistanceHelpId);
    /**
     * 获取社区信息
     */
    public List<UserUniteAssistanceDto> getCommunityAssistance();
}
