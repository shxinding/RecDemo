package xingoo.test.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xingoo.test.utils.HBaseClient;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("page")
public class RecController {
    private static final Long MAX_TIMESTAMP = 9999999999999L;

    @RequestMapping("rec")
    public List<String> rec(String memberId,String start,String end){
        LocalDateTime start_date = LocalDateTime.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime end_date   = LocalDateTime.parse(end,   DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Long end_row = MAX_TIMESTAMP - Timestamp.valueOf(start_date).getTime();
        Long start_row   = MAX_TIMESTAMP - Timestamp.valueOf(end_date).getTime();
        return HBaseClient.scan("rec:userview", "item_id",memberId+start_row,memberId+end_row);
    }
}
