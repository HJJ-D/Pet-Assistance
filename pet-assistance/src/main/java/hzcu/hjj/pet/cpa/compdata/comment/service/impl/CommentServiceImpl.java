package hzcu.hjj.pet.cpa.compdata.comment.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hzcu.hjj.pet.cpa.compdata.comment.entity.Comment;
import hzcu.hjj.pet.cpa.compdata.comment.entity.CommentDto;
import hzcu.hjj.pet.cpa.compdata.comment.mapper.CommentMapper;
import hzcu.hjj.pet.cpa.compdata.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    CommentMapper commentMapper;
    @Override
    public List<CommentDto> getCommentList(String petAssistanceHelpId) {
        return commentMapper.getCommentList(petAssistanceHelpId);
    }
}
