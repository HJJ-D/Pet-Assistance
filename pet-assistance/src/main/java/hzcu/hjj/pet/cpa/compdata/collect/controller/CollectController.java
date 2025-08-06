package hzcu.hjj.pet.cpa.compdata.collect.controller;

import hzcu.hjj.pet.cpa.common.response.ResEntity;
import hzcu.hjj.pet.cpa.compdata.collect.entity.Collect;
import hzcu.hjj.pet.cpa.compdata.collect.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/collect")
public class CollectController {
    @Autowired
    private CollectService collectService;
    /**
     * 添加收藏
     * @param collect
     * @return
     */
    @RequestMapping("/collect")
    public ResEntity<?> collect(@RequestBody Collect collect) {
        if (collectService.saveOrUpdate(collect)) {
            return ResEntity.ok("收藏成功");
        } else {
            return ResEntity.badRequest("收藏失败");
        }
    }
    /**
     * 取消收藏
     * @param collect
     * @return
     */
     @RequestMapping("/cancelCollect")
     public ResEntity<?> cancelCollect(@RequestBody Collect collect) {
         if (collectService.saveOrUpdate(collect)) {
             return ResEntity.ok("取消收藏成功");
         } else {
             return ResEntity.badRequest("取消收藏失败");
         }
     }
    /**
     * 获取点赞情况
     */
    @RequestMapping("/getCollect")
    public ResEntity<?> getLike(@RequestBody Collect collect) {
        return ResEntity.ok(collectService.getCollect(collect));
    }
}
