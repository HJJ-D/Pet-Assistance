package hzcu.hjj.pet.cpa.compdata.assistance.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hzcu.hjj.pet.cpa.compdata.assistance.dto.UserUniteAssistanceDto;
import hzcu.hjj.pet.cpa.compdata.assistance.entity.Assistance;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AssistanceMapper extends BaseMapper<Assistance> {
    /**
     *
     * @param userArea
     * @return
     */
    List<UserUniteAssistanceDto> recommendByArea(String userArea);

    /**
     * 获取单条信息
     * @param petAssistanceHelpId
     * @return
     */
    List<UserUniteAssistanceDto> getAssistance(String petAssistanceHelpId);
    /**
     * 获取社区信息
     */
    List<UserUniteAssistanceDto> getCommunityAssistance();
}
