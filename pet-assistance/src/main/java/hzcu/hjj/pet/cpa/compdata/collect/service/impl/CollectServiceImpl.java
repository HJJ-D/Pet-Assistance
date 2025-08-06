package hzcu.hjj.pet.cpa.compdata.collect.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hzcu.hjj.pet.cpa.compdata.collect.entity.Collect;
import hzcu.hjj.pet.cpa.compdata.collect.mapper.CollectMapper;
import hzcu.hjj.pet.cpa.compdata.collect.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectServiceImpl extends ServiceImpl<CollectMapper, Collect> implements CollectService {

    @Autowired
    private CollectMapper collectMapper;
    @Override
    public List<Collect> getCollect(Collect collect) {
        return collectMapper.getCollect(collect);
    }
}
