package hzcu.hjj.pet.cpa.compdata.like.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hzcu.hjj.pet.cpa.compdata.like.entity.Like;
import hzcu.hjj.pet.cpa.compdata.like.mapper.LikeMapper;
import hzcu.hjj.pet.cpa.compdata.like.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeServiceImpl extends ServiceImpl<LikeMapper, Like> implements LikeService {
    @Autowired
    LikeMapper likeMapper;
    /**
     * 根据点赞人和帖子查询点赞记录
     */
    @Override
    public List<Like> getLike(Like like) {
        return likeMapper.getLike(like);
    }
}
