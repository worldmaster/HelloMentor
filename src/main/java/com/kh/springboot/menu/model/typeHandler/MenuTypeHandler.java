package com.kh.springboot.menu.model.typeHandler;

import com.kh.springboot.menu.model.vo.MenuType;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
* BaseTypeHandler?
* 일반적인 JAVA의 자료형이 아닌 내가 직접 커스텀한 데이터 타입을 처리하기 위해 사용되는 클래스
* DB의 특정 컬럼에 내가 만든 자료형의 값을 대입하고자 할 때 사용한다.
*
* MenuType --> varchar2로 변경하고 싶다 -> setString함수 오버라이딩
* varchar2 --> MenuType로 반환하고싶다 -> getString함수 오버라이딩*/


@MappedTypes(MenuType.class) // 자바에서 사용할 자료형
@MappedJdbcTypes(JdbcType.VARCHAR) // DB에서 사용할 자료형
public class MenuTypeHandler extends BaseTypeHandler<MenuType> {


    /*
    * setNonNullParameter?
    * myBatis 프레임워크에서 SQL문을 완성할 때 사용하는 메서드로 매개변수로 전달받은 데이터가 NULL값인지 아닌지 검사할 때 사용하는 메소드
    *
    * 매개변수로 전달받은 Menu자료형의 MenuType에 담긴 값이 null이 아닐 떄만 실행됨
    * */
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, MenuType parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setString(i, parameter.getValue());
    }


    /*
    * ResultSet의 반환결과가 null이 아닐 때만 실행
    *
    * 반환 결과는 무조건 VARCHAR@ ----> MenuType으로 변경
    *
    * */
    @Override
    public MenuType getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return MenuType.menuTypeValueOf(rs.getString(columnName)); // rs.getString("USER_NO");랑 동일
    }

    @Override
    public MenuType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return MenuType.menuTypeValueOf(rs.getString(columnIndex)); // rs.getString(1);
    }

    @Override
    public MenuType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return MenuType.menuTypeValueOf(cs.getString(columnIndex));
    }
}
