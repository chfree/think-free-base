package com.cditer.sqlparse;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleSchemaStatVisitor;
import com.alibaba.druid.sql.dialect.postgresql.visitor.PGSchemaStatVisitor;
import com.alibaba.druid.stat.TableStat;
import com.alibaba.druid.util.JdbcConstants;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author C.H
 * @email chfree365@qq.com
 * @createtime 2022/10/14 12:22
 * @comment
 */

public class SqlParseUtilTest {

    @Test
    public void test01() {
        String sql = ""
                + "insert into tar select * from boss_table bo, ("
                + "select a.f1, ff from emp_table a "
                + "inner join log_table b "
                + "on a.f2 = b.f3"
                + ") f "
                + "where bo.f4 = f.f5 "
                + "group by bo.f6 , f.f7 having count(bo.f8) > 0 "
                + "order by bo.f9, f.f10\r\n"
                + "select func(f) from test1; "
                + "";

        DbType dbType = JdbcConstants.ORACLE;


        //格式化输出
        String result = SQLUtils.format(sql, dbType);
        System.out.println(result); // 缺省大写格式
        List<SQLStatement> stmtList = SQLUtils.parseStatements(sql, dbType);

        //解析出的独立语句的个数
        System.out.println("size is:" + stmtList.size());
        for (int i = 0; i < stmtList.size(); i++) {
            SQLStatement stmt = stmtList.get(i);

            OracleSchemaStatVisitor visitor = new OracleSchemaStatVisitor();
            stmt.accept(visitor);

            System.out.println(visitor.getRepository());
        }

//        for (int i = 0; i < stmtList.size(); i++) {
//
//            SQLStatement stmt = stmtList.get(i);
//
//            OracleSchemaStatVisitor visitor = new OracleSchemaStatVisitor();
//            stmt.accept(visitor);
//
//            Set<TableStat.Column> groupby_col = visitor.getGroupByColumns();
//            //
//            for (Iterator iterator = groupby_col.iterator(); iterator.hasNext(); ) {
//                TableStat.Column column = (TableStat.Column) iterator.next();
//                System.out.println("[GROUP]" + column.toString());
//            }
//            //获取表名称
//            System.out.println("table names:");
//            Map<TableStat.Name, TableStat> tabmap = visitor.getTables();
//            for (Iterator iterator = tabmap.keySet().iterator(); iterator.hasNext(); ) {
//                TableStat.Name name = (TableStat.Name) iterator.next();
//                System.out.println(name.toString() + " - " + tabmap.get(name).toString());
//            }
//            //System.out.println("Tables : " + visitor.getCurrentTable());
//            //获取操作方法名称,依赖于表名称
//            System.out.println("Manipulation : " + visitor.getTables());
//            //获取字段名称
//            System.out.println("fields : " + visitor.getColumns());
//        }
    }
}
