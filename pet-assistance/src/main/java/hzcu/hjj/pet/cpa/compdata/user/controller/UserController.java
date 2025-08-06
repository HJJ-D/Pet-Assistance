package hzcu.hjj.pet.cpa.compdata.user.controller;

import hzcu.hjj.pet.cpa.common.fileUpload.service.FileUploadService;
import hzcu.hjj.pet.cpa.common.response.ResEntity;
import hzcu.hjj.pet.cpa.common.util.RSAUtils;
import hzcu.hjj.pet.cpa.compdata.user.entity.User;
import hzcu.hjj.pet.cpa.compdata.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Value("${key.private}")
    private String privateKey;
    @Autowired
    private UserService userService;
    @Autowired
    private FileUploadService fileUploadService;

    @RequestMapping("/list")
    public List<User> list(){
        return userService.list();
    }

    /**
     * 根据用户id获取用户
     * @param id
     * @return
     */
    @RequestMapping("/getUserById/{id}")
    public User getUserById(@PathVariable String id){
        return userService.getById(id);
    }

    /**
     * 新建用户
     * @param user
     */
    @RequestMapping("/register")
    public ResEntity<?> insertInfo(@RequestBody User user){
        user.setUserPwd(DigestUtils.md5DigestAsHex((user.getUserPwd()).getBytes()));
        if (userService.save(user)){
            return ResEntity.ok("注册成功");
        } else {
            return ResEntity.badRequest("用户名已存在");
        }
    }

    /**
     * 根据id更新用户表信息
     * @param user
     */
    @RequestMapping("/update")
    public ResEntity<?> updateById(@RequestBody User user){
        if (userService.updateById(user)){
            return ResEntity.ok("更新成功");
        } else {
            return ResEntity.badRequest("更新失败");
        }
    }

    /**
     * 根据id删除用户信息
     * @param id
     */
    @RequestMapping("/delete")
    public void deleteById(String id){
        userService.removeById(id);
    }
    /**
     * 用户登录
     */
    @RequestMapping("/login")
    public ResEntity<?> login(@RequestBody User user, HttpServletResponse response){
        user.setUserPwd(RSAUtils.decryptRSADefault(privateKey, user.getUserPwd()));
        List<User> userList = userService.login(user);
        if(userList.size() == 0){
            return ResEntity.badRequest("用户名或密码错误");
        } else {
            try {
                Map map = new HashMap<>();
                user = userList.get(0);
                String token = userService.createToken(user);
                response.setHeader("authorization", token);
                map.put("token", token);
                map.put("user", user);
                return ResEntity.ok(map);
            } catch (Exception e) {
                return ResEntity.badRequest(e.getMessage());
            }
        }
    }

    /**
     * 登录注销
     */
    @RequestMapping("/logout")
    public ResEntity<?> logout(@RequestBody User user){
        userService.deleteToken(user.getPetAssistanceUserId());
        return ResEntity.ok("注销成功");
    }
    /**
     * 上传头像
     */
    @PostMapping("/headPhotoUpload")
    public ResEntity<?> headPhotoUpload(@RequestParam("file") MultipartFile file, @RequestParam("uid") String uid) throws IOException {
        ResEntity<?> res = fileUploadService.handleFileUpload(file);
        if (res.getStatusCode() == 200) {
            User user = new User();
            user.setUserPhotoUrl(res.getData().toString());
            user.setPetAssistanceUserId(uid);
            userService.updateById(user);
        }
        return res;
    }
}
