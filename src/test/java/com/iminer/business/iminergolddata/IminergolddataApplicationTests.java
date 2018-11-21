package com.iminer.business.iminergolddata;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IminergolddataApplicationTests {

    private MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext wac;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void testInitTime() throws Exception {
        long startTime = System.currentTimeMillis();
        MvcResult mvcResult = mockMvc.perform(post("/gold/guest/add").param("name", "1").accept(MediaType.parseMediaType("application/json;charset=UTF-8"))).andDo(print()).andExpect(status().isOk()).andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        System.out.println(result);
        long endTime = System.currentTimeMillis();
        System.out.println("接口调用时间：" + (endTime - startTime) / 1000 + "s");
    }
}
