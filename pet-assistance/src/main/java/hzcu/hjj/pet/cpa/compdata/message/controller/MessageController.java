package hzcu.hjj.pet.cpa.compdata.message.controller;

import hzcu.hjj.pet.cpa.common.communication.WebSocketServer;
import hzcu.hjj.pet.cpa.common.response.ResEntity;
import hzcu.hjj.pet.cpa.common.util.FastJsonUtils;
import hzcu.hjj.pet.cpa.compdata.message.entity.Message;
import hzcu.hjj.pet.cpa.compdata.message.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Resource
    private WebSocketServer webSocketServer;

    /**
     * 获取消息记录，并更新成为已读
     * @param id1
     * @param id2
     * @return
     */
    @RequestMapping("/getHistory/{id1}/{id2}")
    public ResEntity<?> getHistory(@PathVariable String id1, @PathVariable String id2) {
        messageService.updateHistory(id1, id2);
        return ResEntity.ok(messageService.getHistory(id1, id2));
    }
    /**
     * 发送消息
     */
    @RequestMapping("/sendMessage")
    public ResEntity<?> sendMessage(@RequestBody Message message) throws IOException {
        webSocketServer.sendInfo(message.getToUserId(), FastJsonUtils.convertObjectToJSON(message));
        messageService.save(message);
        return ResEntity.ok("发送成功");
    }
    /**
     * 获取当前用户消息列表
     */
    @RequestMapping("/getMessageList/{currentUserId}")
    public ResEntity<?> getMessageList(@PathVariable String currentUserId) {
        return ResEntity.ok(messageService.getMessageList(currentUserId));
    }
}
