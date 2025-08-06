package hzcu.hjj.pet.cpa.compdata.message.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hzcu.hjj.pet.cpa.compdata.message.entity.Message;
import hzcu.hjj.pet.cpa.compdata.message.entity.MessageUserDto;
import hzcu.hjj.pet.cpa.compdata.message.mapper.MessageMapper;
import hzcu.hjj.pet.cpa.compdata.message.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

    @Autowired
    private MessageMapper messageMapper;
    @Override
    public List<Message> getHistory(String id1, String id2) {
        return messageMapper.getHistory(id1, id2);
    }

    @Override
    public void updateHistory(String id1, String id2) {
        messageMapper.updateHistory(id1, id2);
    }

    @Override
    public List<MessageUserDto> getMessageList(String currentUserId) {
        return messageMapper.getMessageList(currentUserId);
    }
}
