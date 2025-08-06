package hzcu.hjj.pet.cpa.compdata.collect.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hzcu.hjj.pet.cpa.compdata.collect.entity.Collect;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CollectMapper extends BaseMapper<Collect> {
    /**
     * 根据点赞人和帖子查询点赞记录
     */
    List<Collect> getCollect(Collect collect);
}
