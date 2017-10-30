package com.cmos.edccommon.web.controller.serviceswitch;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cmos.common.exception.GeneralException;
import com.cmos.edccommon.beans.serviceswitch.ServiceSwitchDO;
import com.cmos.edccommon.iservice.serviceswitch.IServiceSwitchSV;
import com.cmos.edccommon.web.serviceSwitch.CacheFatctoryUtil;
import com.cmos.edccommon.web.serviceSwitch.JVMCacheDataUtil;
import com.cmos.edccommon.web.serviceSwitch.RedisCacheDataUtil;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 66408 on 2017/10/18.
 */
@RestController
@RequestMapping("/ServiceSwitchController")
@Aspect
public class ServiceSwitchController extends HttpServlet{
    @Autowired
    private CacheFatctoryUtil cacheFatctoryUtil;
    @Autowired
    private RedisCacheDataUtil redisCacheDataUtil;
    @Reference
    private IServiceSwitchSV serviceSwitchSV;
    private static ServiceSwitchController ServiceSwitchController;
    @PostConstruct
    public void init() {
        ServiceSwitchController = this;
        ServiceSwitchController.serviceSwitchSV = this.serviceSwitchSV;
        ServiceSwitchController.cacheFatctoryUtil = this.cacheFatctoryUtil;
    }
    public  void init(ServletConfig config) {
        Map jvm = new HashMap();
        System.out.print("第一个断点");
        jvm.put("pushType","1");
        Map redis = new HashMap();
        redis.put("pushType","2");
        System.out.print("第二个断点");
        Map all = new HashMap();
        all.put("pushType","");
        System.out.println("第三个断点");
        List<ServiceSwitchDO> allList =ServiceSwitchController.serviceSwitchSV.selectByType(all);
//        ServiceSwitchDO serviceSwitchDO = ServiceSwitchController.serviceSwitchSV.getServiceSwitchByKey("edc_oacc");
//        System.out.println(serviceSwitchDO.getSwtchVal());
        System.out.println("第四个断点");
        Map<String,Map<String,String>> allInput = new HashMap<String,Map<String,String>>();
        for(int i=0;i<allList.size();i++){
            if( allList.get(i).getCacheTypeCd()!=null) {
                Map<String, String> c = new HashMap<String, String>();
                c.put("cacheType", allList.get(i).getCacheTypeCd());
                c.put("value", allList.get(i).getSwtchVal());
                allInput.put(allList.get(i).getSwtchKey(), c);
                try {
                    ServiceSwitchController.cacheFatctoryUtil.putStringCacheData(allInput);
                } catch (GeneralException e) {
                    e.printStackTrace();
                }
                System.out.println(allList.get(i).getSwtchKey() + "+" + allList.get(i).getCacheTypeCd() + "+" + allList.get(i).getSwtchVal());
            }
        }
        System.out.println("缓存初始化完了");
    }
    public boolean pushStringToJVM(ServiceSwitchDO serviceSwitchDO) throws GeneralException {
        boolean result = false;
        Map data = new HashMap();
        data.put(serviceSwitchDO.getSwtchKey(),serviceSwitchDO.getSwtchVal());
        result= JVMCacheDataUtil.putStringCache(data);
        return result;
    }
    public boolean pushStringToRedis(ServiceSwitchDO serviceSwitchDO) throws GeneralException {
        boolean result = false;
        Map data = new HashMap();
        data.put(serviceSwitchDO.getSwtchKey(),serviceSwitchDO.getSwtchVal());
        result=redisCacheDataUtil.putStringCache(data);
        return result;
    }
    public boolean insertDataToDB(ServiceSwitchDO serviceSwitchDO) throws GeneralException {
        boolean result = false;
        try{
            serviceSwitchSV.insertSelective(serviceSwitchDO);
        result = true;
    }catch (Exception e){
        e.printStackTrace();
    }
        return result;
    }
    public boolean updataDataToDB(ServiceSwitchDO serviceSwitchDO) throws GeneralException {
        boolean result = false;
        try{
        serviceSwitchSV.updateByPrimaryKeySelective(serviceSwitchDO);
            result = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
