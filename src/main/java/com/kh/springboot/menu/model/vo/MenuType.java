package com.kh.springboot.menu.model.vo;


/*
* java.lang.Enum을 상속
* Enum => Enumeration(열거)의 약자
* 기존 사용하던 상수를 정의하는 방법 중 하나인 static final과 같은 기능을 함
* 문자열 혹은 숫자들을 나타내는 기본 자료형들을 Enum을 통해서 표현할 수 있음
*
* enum 사용시 코드가 간결해지고 가독성이 좋아지며, 동일한 형태의 데이터 그룹을 관리하는데 용이하여 자주 사용되는 자료형이다.
* ex) DB에 저장된 menuType의 값이 KR이든 kr이든 korea이든 다 같은 값으로 취급하고자 할 때 사용함, 0 == false == N
*
* public static final String type1 = "KR";
* public static final String type2 = "kr";
* public static final String type3 = "korea";
*
* 일반적으로 static final 형태로 데이터를 관리했을 때 관리하고자 하는 상수가 많을수록 가독성이 떨어짐
* type1, type2, type3 변수명 자체는 의미를 가지지 못함
*
* */

import com.fasterxml.jackson.annotation.JsonValue;

public enum MenuType {

    // static final 예약어 자동추가, 다른 클래스에서 꺼내쓸 때 MenuType.KR, MenuType.CH등으로 꺼내쓸 수 있음
    // KR CH JP 각 필드에 비슷한 상수요소인 kr ch jp도 함께 저장하고자 하는 경우

    // 1) 그룹화 시킬 값을 각 필드의 매개변수로 추가
    KR("kr"), CH("ch"), JP("jp");

    // 2) 매개변수로 들어온 값을 저장할 필드 추가
    private String value;

    // 3) 생성자 함수 추가 : 각 데이터그룹의 value에는 객체별 kr, ch, jp 문자열 데이터가 담긴다
    MenuType(String value){
        this.value = value;
    }

    // 4) getter 메소드 작성
    @JsonValue
    public String getValue() {
        return value;
    }

    // 5) 매개변수로 들어온 필드값을 통해 일치하는 Enum을 찾는 메소드

    public static MenuType menuTypeValueOf(String value) {
        switch (value){
            case "kr" : return KR;
            case "ch" : return CH;
            case "jp" : return JP;
            default:
                throw new AssertionError("Unknown MenuType : "+value);
        }
    }

    // 5_2) 매개변수로 들어온 필드값을 통해 Enun을 찾는 메소드 2
    public static MenuType menuTypeValueOf2(String value) {

        // MenuType 내부의 상수객체 배열을 반환해줌 -> KR, CH, JP
        MenuType[] menus = MenuType.values();
        for( MenuType m : menus ) {
            if(m.value == value ){ // 반복문을 돌리면서 현재 매개변수로 들어온 값과 일치하는 value를 가진 enum객체를 반환
                return m;
            }
        }
        return null;
    }
}
