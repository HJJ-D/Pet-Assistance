package hzcu.hjj.pet.cpa.compdata.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hzcu.hjj.pet.cpa.compdata.user.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    /**
     * 登录
     * @param user
     * @return
     */
    List<User> login(User user);
}
