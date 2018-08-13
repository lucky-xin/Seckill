package com.xin.seckill.controllers;

import com.xin.seckill.dto.JsonDataPackage;
import com.xin.seckill.dto.ExecutionResultInfo;
import com.xin.seckill.dto.SeckillExposer;
import com.xin.seckill.enums.SeckillStatEnum;
import com.xin.seckill.pojo.Seckill;
import com.xin.seckill.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author Luchaoxin
 * @version V1.0
 * @Description: 秒杀Controller类
 * @date 2018-08-10 16:33
 * @Copyright (C)2018 , Luchaoxin
 */

@Component
@RequestMapping("/seckill")//url:模块/资源/{}/细分
public class SeckillController {

    @Autowired
    private SeckillService seckillService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        //list.pages+mode=ModelAndView
        //获取列表页
        List<Seckill> list = seckillService.getSeckillList();
        model.addAttribute("infos", list);
        return "/pages/seckillList";
    }

    @ResponseBody
    @RequestMapping(value = "/json/list", method = RequestMethod.GET)
    public List<Seckill> jsonList(Model model) {
        //list.pages+mode=ModelAndView
        //获取列表页
        List<Seckill> list = seckillService.getSeckillList();
        model.addAttribute("list", list);
        return list;
    }

    @RequestMapping(value = "/{seckillId}/detail", method = RequestMethod.GET)
    public String detail(@PathVariable("seckillId") Long seckillId, Model model) {

        if (seckillId == null) {
            return "redirect:/seckill/list";
        }
        Seckill seckill = seckillService.getById(seckillId);
        if (seckill == null) {
            return "forward:/seckill/list";
        }
        model.addAttribute("seckill", seckill);
        return "/pages/seckillDetail";
    }

    //ajax ,json暴露秒杀接口的方法
    @ResponseBody
    @RequestMapping(value = "/{seckillId}/exposer", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public JsonDataPackage<SeckillExposer> exposer(@PathVariable("seckillId") Long seckillId) {
        JsonDataPackage<SeckillExposer> result;
        try {
            SeckillExposer exposer = seckillService.exportSeckillUrl(seckillId);
            result = new JsonDataPackage<SeckillExposer>(true, exposer);
        } catch (Exception e) {
            e.printStackTrace();
            result = new JsonDataPackage<SeckillExposer>(false, e.getMessage());
        }

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/{seckillId}/{md5}/execution", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public JsonDataPackage<ExecutionResultInfo> execute(@PathVariable("seckillId") Long seckillId,
                                                        @PathVariable("md5") String md5,
                                                        @CookieValue(value = "userPhone", required = false) Long userPhone) {
        if (userPhone == null) {
            return new JsonDataPackage<ExecutionResultInfo>(false, "未注册");
        }
        JsonDataPackage<ExecutionResultInfo> result;

        try {
            ExecutionResultInfo execution = seckillService.executeSeckill(seckillId, userPhone, md5);
            return new JsonDataPackage<ExecutionResultInfo>(true, execution);
        } catch (Exception e) {
            ExecutionResultInfo execution = new ExecutionResultInfo(seckillId, SeckillStatEnum.INNER_ERROR);
            return new JsonDataPackage<ExecutionResultInfo>(true, execution);
        }

    }

    //获取系统时间
    @RequestMapping(value = "/time/now", method = RequestMethod.GET)
    @ResponseBody
    public JsonDataPackage<Long> time(@RequestParam("date") Date date) {
        Date now = new Date();
        return new JsonDataPackage<Long>(true, now.getTime());
    }

//    @InitBinder
//    public void initBinder(ServletRequestDataBinder binder) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
//    }

}
