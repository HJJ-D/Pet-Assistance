package hzcu.hjj.pet.cpa.compdata.like.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hzcu.hjj.pet.cpa.compdata.like.entity.Like;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LikeMapper extends BaseMapper<Like> {
    /**
     * 根据点赞人和帖子查询点赞记录
     */
    List<Like> getLike(Like like);
}
