package com.iminer.business.iminergolddata.controller;

import com.iminer.business.iminergolddata.common.ResponseObject;
import com.iminer.business.iminergolddata.domain.Guest;
import com.iminer.business.iminergolddata.service.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @ClassName GuestController
 * @Description 嘉宾接口操作controller
 * @Author guowenbin
 * @Date 2018/11/15 11:31
 * @Version 1.0
 **/
@RestController
@RequestMapping("/gold/guest")
public class GuestController {

    @Autowired
    GuestService guestService;

    /**
     * 表单形式添加嘉宾
     *
     * @return
     */
    @RequestMapping(value = "/add", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseObject<Map<String, Object>> addGuest(Guest guest) {
        return new ResponseObject<>(guestService.add(guest));
    }

    /**
     * json形式添加嘉宾
     *
     * @return
     */
    @RequestMapping(value = "/add/one")
    public ResponseObject<Map<String, Object>> addGuestJson(@RequestBody Guest guest) {
        return new ResponseObject<>(guestService.add(guest));
    }

    /**
     * 查询嘉宾
     *
     * @return
     */
    @RequestMapping(value = "query/all", method = RequestMethod.GET)
    public ResponseObject<List<Guest>> findAll() {
        return new ResponseObject<>(guestService.findAll());
    }

    /**
     * jdbcTemplate查询某个嘉宾
     *
     * @return
     */
    @RequestMapping(value = "find/one", method = RequestMethod.GET)
    public ResponseObject<Guest> findOne(String Id) {
        return new ResponseObject<>(guestService.findOne(Id));
    }

}
