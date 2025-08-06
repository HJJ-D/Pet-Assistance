package hzcu.hjj.pet.cpa.compdata.feeder.controller;

import hzcu.hjj.pet.cpa.compdata.feeder.entity.Feeder;
import hzcu.hjj.pet.cpa.compdata.feeder.service.FeederService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FeederController {
    @Autowired
    private FeederService feederService;

    @RequestMapping("/compdata/feeder/list")
    public List<Feeder> list(){
        return feederService.list();
    }

    /**
     * 根据feederid获取用户
     * @param id
     * @return
     */
    @RequestMapping("/compdata/feeder/getFeederById/{id}")
    public Feeder getUserById(@PathVariable String id){
        return feederService.getById(id);
    }

    /**
     * 新建用户
     * @param feeder
     */
    @RequestMapping("/compdata/feeder/newUser")
    public void insertInfo(@RequestBody Feeder feeder){
        feederService.save(feeder);
    }

    /**
     * 根据id更新学生表信息
     * @param feeder
     */
    @RequestMapping("/compdata/feeder/update")
    public void updateById(Feeder feeder){

        feederService.updateById(feeder);
    }

    /**
     * 根据id删除用户信息
     * @param id
     */
    @RequestMapping("/delete/feeder/delete")
    public void deleteById(String id){
        feederService.removeById(id);
    }
}
