package xingoo.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xingoo.test.mapper.RecMetricsMapper;

@RestController
@RequestMapping("metrics")
public class RecMetricsController {

    @Autowired
    private RecMetricsMapper recMetricsMapper;

    @RequestMapping("count")
    public Object count(String event){
        return recMetricsMapper.queryExpose(event);
    }

    @RequestMapping("ctr")
    public Object ctr(){
        return recMetricsMapper.queryCTR();
    }


}
