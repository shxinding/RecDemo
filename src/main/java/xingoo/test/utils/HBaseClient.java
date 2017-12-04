package xingoo.test.utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * HBase客户端
 *
 * @author xingoo
 */
public class HBaseClient {

    public static final byte[] COLUMN_FAMILY = Bytes.toBytes("t");
    private static Connection connection = null;
    private static Logger logger = LoggerFactory.getLogger(HBaseClient.class);

    static {
        try {
            Configuration conf = HBaseConfiguration.create();
            conf.set("hbase.zookeeper.property.clientPort", "2181");
            conf.set("hbase.zookeeper.quorum", "hnode2,hnode4,hnode5");
            connection = ConnectionFactory.createConnection(conf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Table getTable(String table) throws IOException {
        return connection.getTable(TableName.valueOf(table));
    }

    /**
     * 通过指定的table,key,field进行查询，暂时仅支持字符串类型
     * @param table 表名
     * @param key   查询rowkey
     * @param field 字段
     * @return  返回结果
     * @throws IOException 获取table出错
     */
    public static String query(String table,String key,String field) throws IOException {
        String column = "";
        try (Table tab = getTable(table)) {
            Get get = new Get(Bytes.toBytes(key));
            Cell cell = tab.get(get).getColumnLatestCell(COLUMN_FAMILY, Bytes.toBytes(field));
            column = Bytes.toString(CellUtil.cloneValue(cell));
        } catch (Exception e) {
            logger.error("查询请求出错:" + e.getMessage());
        }
        return column;
    }

    public static List<String> scan(String table, String key, String field,String start,String end) {
        List<String> list = new ArrayList<>();
        try(Table tab = getTable(table)){
            Scan scan = new Scan();
            scan.setMaxVersions(1);
            scan.setBatch(100);
            scan.setStartRow(Bytes.toBytes(key+"0000000000000"));
            scan.setStopRow(Bytes.toBytes( key+"9999999999999"));
            ResultScanner rs = tab.getScanner(scan);
            for(Result r : rs){
                Cell cell = r.getColumnLatestCell(COLUMN_FAMILY, Bytes.toBytes(field));
                list.add(Bytes.toString(CellUtil.cloneValue(cell)));
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return list;
//        return list.stream().distinct().collect(Collectors.toList());
    }
}
