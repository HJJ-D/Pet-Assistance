package hzcu.hjj.pet.cpa.compdata.message.service;

import com.baomidou.mybatisplus.extension.service.IService;
import hzcu.hjj.pet.cpa.compdata.message.entity.Message;
import hzcu.hjj.pet.cpa.compdata.message.entity.MessageUserDto;

import java.util.List;

public interface MessageService extends IService<Message> {
    /**
     * 获取聊天记录
     */
    public List<Message> getHistory(String id1, String id2);
    /**
     * 获取聊天记录
     */
    public void updateHistory(String id1, String id2);
    /**
     * 获取当前用户的消息列表
     */
    public List<MessageUserDto> getMessageList(String currentUserId);
}
