package hzcu.hjj.pet.cpa.compdata.assistance.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hzcu.hjj.pet.cpa.compdata.assistance.dto.UserUniteAssistanceDto;
import hzcu.hjj.pet.cpa.compdata.assistance.entity.Assistance;
import hzcu.hjj.pet.cpa.compdata.assistance.mapper.AssistanceMapper;
import hzcu.hjj.pet.cpa.compdata.assistance.service.AssistanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssistanceServiceImpl extends ServiceImpl<AssistanceMapper, Assistance> implements AssistanceService {

    @Autowired
    AssistanceMapper assistanceMapper;

    @Override
    public List<UserUniteAssistanceDto> recommendByArea(String userArea) {
        return assistanceMapper.recommendByArea(userArea);
    }

    @Override
    public List<UserUniteAssistanceDto> getAssistance(String petAssistanceHelpId) {
        return assistanceMapper.getAssistance(petAssistanceHelpId);
    }

    @Override
    public List<UserUniteAssistanceDto> getCommunityAssistance() {
        // TODO 根据偏好推荐
        return assistanceMapper.getCommunityAssistance();
    }
}