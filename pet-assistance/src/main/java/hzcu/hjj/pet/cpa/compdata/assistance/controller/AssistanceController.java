package hzcu.hjj.pet.cpa.compdata.assistance.controller;

import hzcu.hjj.pet.cpa.common.response.ResEntity;
import hzcu.hjj.pet.cpa.compdata.assistance.dto.UserUniteAssistanceDto;
import hzcu.hjj.pet.cpa.compdata.assistance.entity.Assistance;
import hzcu.hjj.pet.cpa.compdata.assistance.service.AssistanceService;
import hzcu.hjj.pet.cpa.compdata.collect.entity.Collect;
import hzcu.hjj.pet.cpa.compdata.collect.service.CollectService;
import hzcu.hjj.pet.cpa.compdata.comment.entity.Comment;
import hzcu.hjj.pet.cpa.compdata.comment.entity.CommentDto;
import hzcu.hjj.pet.cpa.compdata.comment.service.CommentService;
import hzcu.hjj.pet.cpa.compdata.like.entity.Like;
import hzcu.hjj.pet.cpa.compdata.like.service.LikeService;
import hzcu.hjj.pet.cpa.compdata.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 求助帖子和社区帖子二合一（因为两者字段高度重合），isHelp字段判断是否是求助帖子
 */
@RestController
@RequestMapping("/assistance")
public class AssistanceController {

    @Autowired
    private AssistanceService assistanceService;
    @Autowired
    private LikeService likeService;
    @Autowired
    private CollectService collectService;
    @Autowired
    private CommentService commentService;

    @RequestMapping("/list")
    public List<Assistance> list(){
        return assistanceService.list();
    }

    /**
     * 根据求助id获取求助帖子
     * @param id
     * @return
     */
    @RequestMapping("/getAssistanceById/{id}")
    public Assistance getUserById(@PathVariable String id){
        return assistanceService.getById(id);
    }

    /**
     * 新建用户
     * @param assistance
     */
    @RequestMapping("/newAssistance")
    public ResEntity<?> insertInfo(@RequestBody Assistance assistance){
        if (assistanceService.save(assistance)){
            return ResEntity.ok("success");
        }else {
            return ResEntity.badRequest("fail");
        }
    }

    /**
     * 根据id更新求助表信息
     * @param assistance
     */
    @RequestMapping("/update")
    public void updateById(@RequestBody Assistance assistance){

        assistanceService.updateById(assistance);
    }

    /**
     * 根据id删除求助信息
     * @param id
     */
    @RequestMapping("/delete")
    public void deleteById(String id){
        assistanceService.removeById(id);
    }

    /**
     * 根据地区推荐附近求助帖子
     */
    @RequestMapping("/recommend")
    public ResEntity<?> recommend(@RequestBody User user) {
        Map map = new HashMap<>();
        map.put("recommend", assistanceService.recommendByArea(user.getUserArea()));
        map.put("community", assistanceService.getCommunityAssistance());
        return ResEntity.ok(map);
    }
    /**
     * 获取单条帖子以及其评论、点赞、收藏情况
     */
    @RequestMapping("/getAssistance")
    public ResEntity<?> getAssistance(@RequestBody Like like) {
        List<UserUniteAssistanceDto> assistance = assistanceService.getAssistance(like.getPetAssistanceHelpId());
        List<CommentDto> comments = commentService.getCommentList(like.getPetAssistanceHelpId());
        if (assistance.isEmpty()) {
            return ResEntity.badRequest("fail");
        } else {
            UserUniteAssistanceDto res = assistance.get(0);
            Collect collect = new Collect();
            collect.setPetAssistanceHelpId(like.getPetAssistanceHelpId());
            collect.setPetAssistanceUserId(like.getPetAssistanceUserId());
            // 点赞情况判断
            List<Like> likes = likeService.getLike(like);
            List<Collect> collects = collectService.getCollect(collect);
            if (likes.isEmpty()) {
                res.setLikeDeleted(true);
            } else {
                res.setLikeId(likes.get(0).getLikeId());
                res.setLikeDeleted(likes.get(0).getLikeDeleted());
            }
            if (collects.isEmpty()) {
                res.setCollectDeleted(true);
            } else {
                res.setCollectId(collects.get(0).getCollectId());
                res.setCollectDeleted(collects.get(0).getCollectDeleted());
            }
            res.setComment(comments);
            return ResEntity.ok(res);
        }
    }
}
