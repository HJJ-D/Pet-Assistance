package hzcu.hjj.pet.cpa.compdata.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hzcu.hjj.pet.cpa.common.jwt.entity.JwtEntity;
import hzcu.hjj.pet.cpa.common.jwt.util.JwtUtil;
import hzcu.hjj.pet.cpa.common.util.RedisUtil;
import hzcu.hjj.pet.cpa.compdata.user.entity.User;
import hzcu.hjj.pet.cpa.compdata.user.mapper.UserMapper;
import hzcu.hjj.pet.cpa.compdata.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserMapper userMapper;
    @Override
    public List<User> login(User user) {
        return userMapper.login(user);
    }

    @Override
    public String createToken(User user) {
        String uid = user.getPetAssistanceUserId();
        Instant now = Instant.now();
        String token = JwtUtil.createToken(user.getPetAssistanceUserId(), now);
        LocalDateTime lastLoginTime = LocalDateTime.ofInstant(now, ZoneId.systemDefault());
        RedisUtil.set(uid, new JwtEntity(token, lastLoginTime, true));
        return token;
    }

    @Override
    public void deleteToken(String uid) {
        RedisUtil.del(uid);
    }
}
