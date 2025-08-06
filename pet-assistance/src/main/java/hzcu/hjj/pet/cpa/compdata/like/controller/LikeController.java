package hzcu.hjj.pet.cpa.compdata.like.controller;

import hzcu.hjj.pet.cpa.common.response.ResEntity;
import hzcu.hjj.pet.cpa.compdata.like.entity.Like;
import hzcu.hjj.pet.cpa.compdata.like.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/like")
public class LikeController {
    @Autowired
    private LikeService likeService;

    /**
     * 点赞
     * @param like
     * @return
     */
    @RequestMapping("/like")
    public ResEntity<?> like(@RequestBody Like like) {
        if (likeService.saveOrUpdate(like)) {
            return ResEntity.ok("点赞成功");
        } else {
            return ResEntity.badRequest("点赞失败");
        }
    }
    /**
     * 取消点赞
     */
    @RequestMapping("/cancelLike")
    public ResEntity<?> cancelLike(@RequestBody Like like) {
        if (likeService.saveOrUpdate(like)) {
            return ResEntity.ok("取消点赞成功");
        } else {
            return ResEntity.badRequest("取消点赞失败");
        }
    }
    /**
     * 获取点赞情况
     */
    @RequestMapping("/getLike")
    public ResEntity<?> getLike(@RequestBody Like like) {
        return ResEntity.ok(likeService.getLike(like));
    }
}
