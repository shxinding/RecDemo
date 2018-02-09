package xingoo.test.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import xingoo.test.bean.CountMetrics;

import java.util.List;

@Mapper
public interface RecMetricsMapper {

    @Select("select count(1) as count from metrics")
    Integer count();

    @Select("select scp,modify_time as time,sum(count) as count from metrics group by scp,modify_time order by modify_time;")
    public List<CountMetrics> queryCount();

    @Select("select \n" +
            "  t1.model as name, \n" +
            "  t1.time as time, \n" +
            "  100*(IFNULL(t2.count,0)/t1.count) as count \n" +
            "from ( \n" +
            "select model,DATE_FORMAT(modify_time,'%Y-%m-%d %H') as time,sum(count) as count from metrics \n" +
            "where event = 'expose' and modify_time > DATE_SUB(NOW(),INTERVAL  1 DAY) \n" +
            "group by model,DATE_FORMAT(modify_time,'%Y-%m-%d %H') \n" +
            ") t1 left join ( \n" +
            "select model,DATE_FORMAT(modify_time,'%Y-%m-%d %H') as time,sum(count) as count from metrics \n" +
            "where event = 'click' and modify_time > DATE_SUB(NOW(),INTERVAL  1 DAY) \n" +
            "group by model,DATE_FORMAT(modify_time,'%Y-%m-%d %H') \n" +
            ") t2 on t1.model = t2.model and t1.time = t2.time \n" +
            "order by time")
    public List<CountMetrics> queryCTR();

    @Select("select model as name,DATE_FORMAT(modify_time,'%Y-%m-%d %H') as time,sum(count) as count from metrics \n" +
            "where event = #{event} and modify_time > DATE_SUB(NOW(),INTERVAL  1 DAY)\n" +
            "group by model,DATE_FORMAT(modify_time,'%Y-%m-%d %H') order by modify_time")
    public List<CountMetrics> queryExpose(String event);
}
