package com.qf.shop_item.controller;

import com.qf.entity.goods;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/itemController")
public class itemController {
    @Autowired
    Configuration configuration;
    @RequestMapping("/addPage")
    public void addPage(@RequestBody goods goods, HttpServletRequest request){
        Writer writer = null;
        try {

            Template template = configuration.getTemplate("item.ftl");
            Map<String,Object> map = new HashMap<>();
            map.put("goods",goods);
            map.put("context",request.getContextPath());
            writer = new FileWriter(this.getClass().getResource("/").getPath()+"static/pages/"+goods.getId()+".html");
            //System.out.println(this.getClass().getResource("/").getPath());
            template.process(map,writer);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(writer!=null){
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
