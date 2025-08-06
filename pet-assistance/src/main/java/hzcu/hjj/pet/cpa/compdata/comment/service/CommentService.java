package hzcu.hjj.pet.cpa.compdata.comment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import hzcu.hjj.pet.cpa.compdata.comment.entity.Comment;
import hzcu.hjj.pet.cpa.compdata.comment.entity.CommentDto;

import java.util.List;

public interface CommentService extends IService<Comment> {
    /**
     * 获取评论
     */
    public List<CommentDto> getCommentList(String petAssistanceHelpId);
}
