package com.stylefeng.guns;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.order.OrderServiceAPI;
import com.stylefeng.guns.order.common.persistence.dao.MoocOrderTMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class GunsRestApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Test
	public void mytest() {
		Integer demo1 = 100;
		int demo2 = 100;

		Integer demo3 = 10000;
		int demo4 = 10000;

		Integer demo5 = 100;
		Integer demo6 = 100;

		Integer demo7 = 10000;
		Integer demo8 = 10000;

		int demo10=10000;
		int demo9=10000;
		System.out.println(demo1==demo2);
		System.out.println(demo3==demo4);

		System.out.println(demo5==demo6);
		System.out.println(demo7==demo8);

		System.out.println(demo9==demo10);
	}



	@Test
	public void mytest2() {


	}
}
