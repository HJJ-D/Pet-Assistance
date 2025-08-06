package hzcu.hjj.pet.cpa.compdata.collect.service;

import com.baomidou.mybatisplus.extension.service.IService;
import hzcu.hjj.pet.cpa.compdata.collect.entity.Collect;
import hzcu.hjj.pet.cpa.compdata.like.entity.Like;

import java.util.List;

public interface CollectService extends IService<Collect> {
    /**
     * 根据点赞人和帖子查询点赞记录
     */
    public List<Collect> getCollect(Collect collect);
}
