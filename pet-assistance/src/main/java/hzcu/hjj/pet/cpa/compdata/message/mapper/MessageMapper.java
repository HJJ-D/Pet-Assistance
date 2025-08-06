package hzcu.hjj.pet.cpa.compdata.message.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hzcu.hjj.pet.cpa.compdata.message.entity.Message;
import hzcu.hjj.pet.cpa.compdata.message.entity.MessageUserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MessageMapper extends BaseMapper<Message> {
    /**
     * 获取消息记录
     */
    List<Message> getHistory(String id1, String id2);
    /**
     * 已读消息记录
     */
    void updateHistory(String id1, String id2);
    /**
     * 获取当前用户的消息列表
     */
    List<MessageUserDto> getMessageList(String currentUserId);
}
