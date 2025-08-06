package hzcu.hjj.pet.cpa.compdata.comment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hzcu.hjj.pet.cpa.compdata.comment.entity.Comment;
import hzcu.hjj.pet.cpa.compdata.comment.entity.CommentDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
    /**
     * 获取评论
     * @param petAssistanceHelpId
     * @return
     */
    List<CommentDto> getCommentList(String petAssistanceHelpId);
}
