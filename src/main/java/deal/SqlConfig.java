package deal;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2018/6/25.
 */
public class SqlConfig {

    private String tableName;
    private Connection conn;
    public SqlConfig(String tableName, Connection conn){
            this.tableName = tableName;
            this.conn = conn;
    }

    //得到值为0的表字段
    public List<Integer> getZeroList(int tableIndex,String date) throws Exception {
        List<Integer> list = new ArrayList<Integer>();
        //添加一个初始值，防止字段呗remove后list是空的情况
//        list.add(0);
        //查询表某一天的值
        StringBuffer sb = new StringBuffer();
        String sql1 = "select * from "+tableName +" where stat_time = '"+date+"'";
        PreparedStatement ps = conn.prepareStatement(sql1);
        ResultSet rs = ps.executeQuery();
        ResultSetMetaData rd = rs.getMetaData();
        while(rs.next()){
            int id = rs.getInt(1);
            for(int i = 3;i<= tableIndex;i++){
                if(!rd.getColumnTypeName(i).equals("DECIMAL")){
                    continue;
                }
                BigDecimal indexValue = rs.getBigDecimal(i);
                if(indexValue.compareTo(new BigDecimal(0)) == 0){
//                    System.out.println("数据为0 的字段下标为："+i);
                    list.add(i);
                }
//                else{
//                    //因为有些数据是分组数据，只要有1列大于0就可以remove掉
//                    list.remove(i);
//                }
            }
        }
        return list;
    }

    public int getCount() throws Exception{
        //查询表所有字段的个数
        StringBuffer sb2 = new StringBuffer();
        sb2.append("select count(*)")
                .append(" from ")
                .append("information_schema.COLUMNS ")
                .append("where TABLE_SCHEMA = ")
                .append(" 'v3_stat_inter' ")
                .append(" and table_name = '")
                .append(tableName)
                .append("'");
        PreparedStatement ps2 = conn.prepareStatement(sb2.toString());
        ResultSet rs2 = ps2.executeQuery();
        int tableIndex = 0;
        //表总共的字段
        while(rs2.next()){
            tableIndex = rs2.getInt(1);
        }
        return tableIndex;
    }
    //获取表详情
    public Map<Integer,String> getColumnDetail() throws Exception{
        //获取表字段详细信息
        StringBuffer sb3 = new StringBuffer();
        sb3.append("select ORDINAL_POSITION,COLUMN_NAME,COLUMN_COMMENT from ")
                .append("information_schema.COLUMNS ")
                .append("where TABLE_SCHEMA = ")
                .append(" 'v3_stat_inter' ")
                .append(" and table_name = '")
                .append(tableName)
                .append("'");
        PreparedStatement ps3 = conn.prepareStatement(sb3.toString());
        ResultSet rs3 = ps3.executeQuery();
        Map<Integer,String> map = new HashMap<Integer, String>();
        while(rs3.next()){
            int index = rs3.getInt(1);
            String columnName = rs3.getString(2);
            String columnComment = rs3.getString(3);
            String comment = columnComment + "("+columnName+")";
//            System.out.println(tableName+":"+comment+","+index);
            map.put(index,comment);
        }
        return map;
    }

    //获取表的权限
//    public String
}
