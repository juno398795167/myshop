package com.qf.shop_item;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopItemApplicationTests {
	@Autowired
	Configuration configuration;

	@Test
	public void contextLoads() throws Exception {
		Template template = configuration.getTemplate("hello.ftl");
		Map<String,String> map = new HashMap<>();
		map.put("key","大家好啊");
		Writer writer = new FileWriter("C:\\Users\\Administrator\\Desktop\\myhello.html");
		template.process(map,writer);
		writer.close();
	}



}
