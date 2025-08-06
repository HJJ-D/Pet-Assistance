package hzcu.hjj.pet.cpa.compdata.comment.controller;

import hzcu.hjj.pet.cpa.common.response.ResEntity;
import hzcu.hjj.pet.cpa.compdata.comment.entity.Comment;
import hzcu.hjj.pet.cpa.compdata.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    CommentService commentService;

    @RequestMapping("/comment")
    public ResEntity comment(@RequestBody Comment comment) {
        return ResEntity.ok(commentService.save(comment));
    }

    @RequestMapping("/getCommentList/{petAssistanceHelpId}")
    public ResEntity getCommentList(@PathVariable String petAssistanceHelpId) {
        return ResEntity.ok(commentService.getCommentList(petAssistanceHelpId));
    }
}
