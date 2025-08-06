package hzcu.hjj.pet.cpa.compdata.like.service;

import com.baomidou.mybatisplus.extension.service.IService;
import hzcu.hjj.pet.cpa.compdata.like.entity.Like;

import java.util.List;

public interface LikeService extends IService<Like> {
    /**
     * 根据点赞人和帖子查询点赞记录
     */
    public List<Like> getLike(Like like);
}
