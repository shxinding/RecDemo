package xingoo.test.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xingoo.test.utils.HBaseClient;

import java.util.List;

@RestController
@RequestMapping("page")
public class RecController {
    @RequestMapping("rec")
    public List<String> rec(String memberId){
        return HBaseClient.scan("rec:userview", memberId, "item_id","","");
    }
}
