package com.iminer.business.iminergolddata.common;

import com.iminer.common.db.util.DBKit;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseDbOperateHelper {

    JdbcTemplate writeJdbcTemplate;

    public <T> List<T> searchDataList(String sql, Class<T> classType) {
        return writeJdbcTemplate.query(sql, new BeanPropertyRowMapper<T>(classType));
    }

    /**
     * 根据指定的列名，和 where条件进行查询列表
     * 
     * @param sqlColumnsStr
     *            列名 country_id
     * @param sqlWhereStr
     *            如 where a=123
     * @param classType
     * @return
     */
    public <T> List<T> searchDataList(String sqlColumnsStr, String sqlWhereStr, Class<T> classType) {
        return writeJdbcTemplate.query("select  " + (sqlColumnsStr == null ? " * " : "".equals(sqlColumnsStr.trim()) ? " * " : sqlColumnsStr) + " from " + DBKit.getTableNameByClassName(classType) + " " + (sqlWhereStr == null ? " where 1=1 " : "".equals(sqlWhereStr.trim()) ? "where 1=1" : sqlWhereStr), new BeanPropertyRowMapper<T>(classType));
    }

    /**
     * 根据指定的字段名，和where条件进行查询列表
     * 
     * @param listOfFieldName
     *            类中的字段值：Lists.newArrayList("countryId","id")
     * @param sqlWhereStr
     *            sqlWhereStr 如 where a=123
     * @param classType
     * @return
     */
    public <T> List<T> searchDataList(List<String> listOfFieldName, String sqlWhereStr, Class<T> classType) {
        if (listOfFieldName.isEmpty()) {
            throw new IllegalArgumentException("属性列表不能为空");
        }
        StringBuilder sqlColumnsBuilder = new StringBuilder();
        for (String fieldName : listOfFieldName) {
            sqlColumnsBuilder.append(DBKit.changeFieldToColumnName(fieldName)).append(",");
        }

        String sqlColumnsStr = sqlColumnsBuilder.toString();

        if ("".equals(sqlColumnsStr.trim())) {
            throw new IllegalArgumentException("属性列表转换数据表字段失败");
        }

        sqlColumnsStr = sqlColumnsStr.substring(0, sqlColumnsStr.length() - 1);

        return this.searchDataList(sqlColumnsStr, sqlWhereStr, classType);
    }

    public List<Map<String, Object>> searchDataList(String sql) {
        return writeJdbcTemplate.queryForList(sql);
    }


    public List<Map<String, Object>> queryForList(String sql, Object... args) {
        return writeJdbcTemplate.queryForList(sql, args);
    }

    public <T> List<T> queryForList(String sql, Class<T> classTyple, Object... args) {
        return writeJdbcTemplate.query(sql, args, new BeanPropertyRowMapper<T>(classTyple));
    }

    public <T> List<T> queryForListWithOutNullParams(String sql, Class<T> classTyple, Object... args) {
        List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < args.length; i++) {
            if (args[i] != null) {
                list.add(args[i]);
            }
        }
        return writeJdbcTemplate.query(sql, list.toArray(), new BeanPropertyRowMapper<T>(classTyple));
    }

    public <T> List<T> query(String sql, RowMapper<T> rowMapper) {
        return writeJdbcTemplate.query(sql, rowMapper);
    }

    public <T> List<T> query(String sql, Object[] args, RowMapper<T> rowMapper) {
        return writeJdbcTemplate.query(sql, args, rowMapper);
    }

    public <T> List<T> query(String sql, RowMapper<T> rowMapper, Object... args) {
        return writeJdbcTemplate.query(sql, rowMapper, args);
    }

    public <T> List<T> searchDataList(Object o, Class<T> classTyple) {
        final Map<String, FieldsValuesID> map = (HashMap<String, FieldsValuesID>) formatterClass(o);
        ArrayList<String> namesArr = map.get("FieldsValuesID").fields;
        ArrayList<Object> values = map.get("FieldsValuesID").values;
        StringBuilder names = new StringBuilder();

        for (int i = 0; i < namesArr.size(); i++) {
            names.append(namesArr.get(i) + "=?");
            if (i < namesArr.size() - 1) {
                names.append(" and ");
            }
        }
        String sql = "select * from " + DBKit.getTableNameByClassName(o) + " where " + names.toString();
        return writeJdbcTemplate.query(sql, values.toArray(), new BeanPropertyRowMapper<T>(classTyple));
    }

    public Map<String, Object> queryForMap(String sql, Object[] args) {
        return writeJdbcTemplate.queryForMap(sql, args);
    }

    public Map<String, Object> queryForMap(String sql) {
        return writeJdbcTemplate.queryForMap(sql);
    }

    public <T> T searchDataInfo(String sql, Class<T> classType) {
        List<T> listOfT = this.searchDataList(sql, classType);
        if (listOfT.size() >= 1) {
            return listOfT.get(0);
        } else {
            return null;
        }
    }

    public <T> T searchDataInfo(String fields, String where, Class<T> classType) {
        List<T> listOfT = this.searchDataList(fields, where, classType);
        if (listOfT.size() >= 1) {
            return listOfT.get(0);
        } else {
            return null;
        }
    }

    public <T> T queryForObject(String sql, Object[] args, RowMapper<T> rowMapper) {
        T infoOfT = writeJdbcTemplate.queryForObject(sql, args, rowMapper);
        return infoOfT;
    }

    public <T> T queryForObject(String sql, Class<T> requiredType, Object... args) {
        T infoOfT = writeJdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<T>(requiredType), args);
        return infoOfT;
    }

    public <T> T queryForObject(String sql, Object[] args, Class<T> classType) {
        return writeJdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper<T>(classType));
    }

    public <T> T queryForCount(String sql, Class<T> requiredType, Object... args) {
        T infoOfT = writeJdbcTemplate.queryForObject(sql, requiredType, args);
        return infoOfT;
    }

    public <T> T queryForCountWithOutNullParams(String sql, Class<T> classTyple, Object... args) {
        List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < args.length; i++) {
            if (args[i] != null) {
                list.add(args[i]);
            }
        }
        T infoOfT = writeJdbcTemplate.queryForObject(sql, classTyple, list.toArray());
        return infoOfT;
    }

    public <T> T queryForCount(String sql, Class<T> requiredType) {
        T infoOfT = writeJdbcTemplate.queryForObject(sql, requiredType);
        return infoOfT;
    }

    public <T> T queryForObject(String sql, Class<T> classTyple) {
        return writeJdbcTemplate.queryForObject(sql, classTyple);
    }

    public Map<String, Object> searchDataInfo(String sql) {
        List<Map<String, Object>> listOfT = this.searchDataList(sql);
        if (listOfT != null && listOfT.size() >= 1) {
            return listOfT.get(0);
        } else {
            return null;
        }
    }

    public boolean insertDb(String sql) {
        boolean returnResult = false;
        if (writeJdbcTemplate.update(sql) > 0) {
            returnResult = true;
        }
        return returnResult;
    }

    public int insertDb(String sql, Object... args) {
        return writeJdbcTemplate.update(sql, args);
    }

    /**
     * 插入记录并获取自增主键
     * @param o
     * @return
     */
    public Long insertDbAndGetKey(String sql) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        writeJdbcTemplate.update(new PreparedStatementCreator(){
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                return ps;
            }
        },keyHolder);
        return keyHolder.getKey().longValue();
    }
    
    /**
     * 插入记录并获取自增主键
     * 
     * @param o
     * @param tableName
     * @return
     */
    public <T> Long insertAndGetKey(Object o, Class<T> classType) {

        String tableName = DBKit.getTableNameByClassName(classType);
        final Map<String, FieldsValuesID> map = (HashMap<String, FieldsValuesID>) formatterClass(o);
        ArrayList<String> namesArr = map.get("FieldsValuesID").fields;
        final ArrayList<Object> values = map.get("FieldsValuesID").values;
        StringBuilder names = new StringBuilder();
        StringBuilder valueHolder = new StringBuilder();
        for (int i = 0; i < namesArr.size(); i++) {
            names.append(namesArr.get(i));
            valueHolder.append("?");
            if (i < namesArr.size() - 1) {
                names.append(",");
                valueHolder.append(",");
            }
        }
        final String sql = "insert into " + tableName + " (" + names + ") values (" + valueHolder.toString() + ")";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        writeJdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                for (int i = 0; i < values.size(); i++) {
                    ps.setObject(i + 1, values.get(i));
                }
                return ps;
            }
        }, keyHolder);
        Long generatedId = keyHolder.getKey().longValue();
        return generatedId;
    }

    public <T> boolean updateDb(String tableAfterSql, Class<T> classType) {
        boolean returnResult = false;
        String tableName = DBKit.getTableNameByClassName(classType);
        String sql = "update " + tableName + " " + tableAfterSql;
        if (writeJdbcTemplate.update(sql) > 0) {
            returnResult = true;
        }
        return returnResult;
    }

    public <T> Long insertDataAndGetKey(Object o, Class<T> classType) {
        String table_name = DBKit.getTableNameByClassName(o);
        final Map<String, FieldsValuesID> map = (HashMap<String, FieldsValuesID>) formatterClass(o);
        ArrayList<String> namesArr = map.get("FieldsValuesID").fields;
        final ArrayList<Object> values = map.get("FieldsValuesID").values;
        StringBuilder names = new StringBuilder();
        StringBuilder valueHolder = new StringBuilder();
        for (int i = 0; i < namesArr.size(); i++) {
            names.append(namesArr.get(i));
            valueHolder.append("?");
            if (i < namesArr.size() - 1) {
                names.append(",");
                valueHolder.append(",");
            }
        }
        KeyHolder keyHolder = new GeneratedKeyHolder();
        final String sql = "insert into " + table_name + " (" + names + ") values (" + valueHolder.toString() + ")";
        int status = writeJdbcTemplate.update(new PreparedStatementCreator(){
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                for (int j = 0; j < values.size(); j++) {
                    Object value = values.get(j);
                    ps.setObject(j + 1, value);
                }
                return ps;
            }
        },keyHolder);

        if (status > 0) {
            return keyHolder.getKey().longValue();
        }else{
            return -1L;
        }
    }
    
    public <T> Boolean insertData(Object o, Class<T> classType) {
        boolean returnResult = false;
        String tableName = DBKit.getTableNameByClassName(o);
        final Map<String, FieldsValuesID> map = (HashMap<String, FieldsValuesID>) formatterClass(o);
        ArrayList<String> namesArr = map.get("FieldsValuesID").fields;
        final ArrayList<Object> values = map.get("FieldsValuesID").values;
        StringBuilder names = new StringBuilder();
        StringBuilder valueHolder = new StringBuilder();
        for (int i = 0; i < namesArr.size(); i++) {
            names.append(namesArr.get(i));
            valueHolder.append("?");
            if (i < namesArr.size() - 1) {
                names.append(",");
                valueHolder.append(",");
            }
        }
        final String sql = "insert into " + tableName + " (" + names + ") values (" + valueHolder.toString() + ")";
        if (writeJdbcTemplate.update(sql, new ValuesPreparedStatementSetter(values.toArray())) > 0) {
            returnResult = true;
        }
        return returnResult;
    }

    public <T> Boolean insertData(Object o, String tableName) {
        boolean returnResult = false;
        final Map<String, FieldsValuesID> map = (HashMap<String, FieldsValuesID>) formatterClass(o);
        ArrayList<String> namesArr = map.get("FieldsValuesID").fields;
        final ArrayList<Object> values = map.get("FieldsValuesID").values;
        StringBuilder names = new StringBuilder();
        StringBuilder valueHolder = new StringBuilder();
        for (int i = 0; i < namesArr.size(); i++) {
            names.append(namesArr.get(i));
            valueHolder.append("?");
            if (i < namesArr.size() - 1) {
                names.append(",");
                valueHolder.append(",");
            }
        }
        final String sql = "insert into " + tableName + " (" + names + ") values (" + valueHolder.toString() + ")";
        if (writeJdbcTemplate.update(sql, new ValuesPreparedStatementSetter(values.toArray())) > 0) {
            returnResult = true;
        }
        return returnResult;
    }

    public boolean updateDb(String sql) {
        boolean returnResult = false;
        if (writeJdbcTemplate.update(sql) > 0) {
            returnResult = true;
        }
        return returnResult;
    }

    public <T> Boolean updateData(Object o, Class<T> classType) {
        return this.updateData(o, classType, null);
    }

    public <T> Boolean updateData(Object o, Class<T> classType, String fieldName) {
        String tableName = DBKit.getTableNameByClassName(o);
        if (fieldName == null) {
            fieldName = DBKit.getTablePrimaryKey(o);
        }
        final Map<String, FieldsValuesID> map = (HashMap<String, FieldsValuesID>) formatterClass(o, fieldName);
        ArrayList<String> namesArr = map.get("FieldsValuesID").fields;
        ArrayList<Object> values = map.get("FieldsValuesID").values;
        values.add(map.get("FieldsValuesID").id);
        StringBuilder names = new StringBuilder();

        for (int i = 0; i < namesArr.size(); i++) {
            names.append(namesArr.get(i) + "=?");
            if (i < namesArr.size() - 1) {
                names.append(",");
            }
        }
        String sql = "update " + tableName + " set " + names.toString() + " where " + DBKit.changeFieldToColumnName(fieldName) + "=?";
        writeJdbcTemplate.update(sql, new ValuesPreparedStatementSetter(values.toArray()));
        return true;
    }

    public int delete(String sql, Object... args) {
        return writeJdbcTemplate.update(sql, args);
    }

    public boolean deleteData(String sql) {
        boolean returnResult = false;
        if (writeJdbcTemplate.update(sql) > 0) {
            returnResult = true;
        }
        return returnResult;
    }

    public <T> Boolean deleteData(Object o) {
        return this.deleteData(o, null);
    }

    public <T> Boolean deleteData(Object o, String fieldName) {
        boolean flag = false;
        String tableName = DBKit.getTableNameByClassName(o);
        if (fieldName == null) {
            fieldName = DBKit.getTablePrimaryKey(o);
        }
        Map<String, FieldsValuesID> map = (HashMap<String, FieldsValuesID>) formatterClass(o, fieldName);
        String sql = "delete from " + tableName + " where " + DBKit.changeFieldToColumnName(fieldName) + "=?";
        int result = writeJdbcTemplate.update(sql, new ValuesPreparedStatementSetter(new Object[] { map.get("FieldsValuesID").id }));
        if (result >= 1) {
            flag = true;
        }
        return flag;
    }

    public int update(String sql, Object[] args, int[] argTypes) {
        return writeJdbcTemplate.update(sql, args, argTypes);
    }

    public int update(String sql, Object... args) {
        return writeJdbcTemplate.update(sql, args);
    }

    public int[] batchUpdate(String sql, List<Object[]> args) {
        return writeJdbcTemplate.batchUpdate(sql, args);
    }

    public int update(PreparedStatementCreator psc, KeyHolder generatedKeyHolder) {
        return writeJdbcTemplate.update(psc, generatedKeyHolder);
    }

    private Map<String, FieldsValuesID> formatterClass(Object object) {
        return this.formatterClass(object, DBKit.getTablePrimaryKey(object));
    }

    private Map<String, FieldsValuesID> formatterClass(Object object, String keyName) {
        Field[] fieldArr = object.getClass().getDeclaredFields();
        Map<String, FieldsValuesID> map = new HashMap<String, FieldsValuesID>();
        int len = fieldArr.length;
        FieldsValuesID fieldsValuesID = new FieldsValuesID();
        for (int i = 0; i < len; i++) {
            Field field = fieldArr[i];
            field.setAccessible(true);
            String name = field.getName();
            if (name.equals("serialVersionUID")) {
                continue;
            }
            String nameAfter = name.substring(0, 1).toUpperCase() + name.substring(1);
            Method m;
            try {
                try {
                    m = object.getClass().getMethod("get" + nameAfter);
                } catch (NoSuchMethodException e) {
                    continue;
                }

                Object value = m.invoke(object);
                if (value == null || value.equals("null")) {
                    continue;
                }
                if (name.equals(keyName)) {
                    fieldsValuesID.id = value;
                }
                fieldsValuesID.fields.add(DBKit.changeFieldToColumnName(name));
                fieldsValuesID.values.add(value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        map.put("FieldsValuesID", fieldsValuesID);
        return map;
    }

    class FieldsValuesID {
        Object id;
        ArrayList<String> fields = new ArrayList<String>();
        ArrayList<Object> values = new ArrayList<Object>();
    }

    class ValuesPreparedStatementSetter implements PreparedStatementSetter {

        public ValuesPreparedStatementSetter(Object[] values) {
            super();
            this.values = values;
        }

        Object[] values;

        @Override
        public void setValues(PreparedStatement ps) throws SQLException {

            for (int j = 0; j < values.length; j++) {
                Object value = values[j];
                ps.setObject(j + 1, value);
            }
        }
    }
}
