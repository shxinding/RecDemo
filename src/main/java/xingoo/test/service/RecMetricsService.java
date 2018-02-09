package xingoo.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xingoo.test.mapper.RecMetricsMapper;

@Service
public class RecMetricsService {

    @Autowired
    private RecMetricsMapper metricsMapper;

    public Integer count(){
        return metricsMapper.count();
    }
}
