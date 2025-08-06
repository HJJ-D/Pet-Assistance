package hzcu.hjj.pet.cpa.compdata.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import hzcu.hjj.pet.cpa.compdata.user.entity.User;

import java.util.List;

public interface UserService extends IService<User> {
    public List<User> login(User user);

    /**
     * 生成token
     */
    public String createToken(User user);
    /**
     * 删除token
     */
    public void deleteToken(String uid);
}
