package sample.cafekiosk.unit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sample.cafekiosk.unit.beverage.Americano;
import sample.cafekiosk.unit.beverage.Latte;
import sample.cafekiosk.unit.order.Order;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CafeKioskTest {

    @Test
    void add_manual_test() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());

        //결국 사람이 콘솔로 검증한다. 다른 사람은 어떤게 맞는 건지 알 수 없다.
        //무조건 성공하는 코드
        System.out.println(">>> 담긴 음료 수 : "+ cafeKiosk.getBeverages().size());
        System.out.println(">>> 담긴 음료 : "+ cafeKiosk.getBeverages().get(0).getName());
    }

    //DisplayName을 섬세하게
    //명사의 나열보다 문장으로 기술
    //테스트 행위에 대한 결과까지 기술
    //도메인 용어를 사용하여 한층 추상화된 내용을 담기
    //테스트의 현상을 중점으로 기술하지 말 것
//    @DisplayName("음료 1개를 추가 테스트")
    @DisplayName("음료 1개를 추가하면 주문 목록에 담긴다.")
    @Test
    void add() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());

        //자동화 테스트
        assertThat(cafeKiosk.getBeverages().size()).isEqualTo(1);
        assertThat(cafeKiosk.getBeverages().get(0).getName()).isEqualTo("아메리카노");
    }

    @Test
    void addSeveralBeverages() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        cafeKiosk.add(americano, 2);

        //자동화 테스트
        assertThat(cafeKiosk.getBeverages().size()).isEqualTo(2);
        assertThat(cafeKiosk.getBeverages().get(0)).isEqualTo(americano);
        assertThat(cafeKiosk.getBeverages().get(1)).isEqualTo(americano);
    }

    @Test
    void addZeroBeverages() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        //자동화 테스트
        assertThatThrownBy(() -> cafeKiosk.add(americano, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("음료는 1잔 이상 주문하실 수 있습니다.");
    }

    @Test
    void remove() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        //추가
        cafeKiosk.add(americano);
        assertThat(cafeKiosk.getBeverages()).hasSize(1);

        //삭제
        cafeKiosk.remove(americano);
        assertThat(cafeKiosk.getBeverages()).isEmpty();
    }

    @Test
    void clear() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        Latte latte = new Latte();

        //추가
        cafeKiosk.add(americano);
        cafeKiosk.add(latte);
        assertThat(cafeKiosk.getBeverages()).hasSize(2);

        //클리어
        cafeKiosk.clear();
        assertThat(cafeKiosk.getBeverages()).isEmpty();
    }

    @Test //TDD 사이클로 개발
    void calculateTotalPrice() {
        //RED -> GREEN -> REFACTOR
        //선 기능 구현, 후 테스트 작성
        //테스트 자체의 누락 가능성
        //특정 테스트 케이스(해피 케이스)만 검증할 가능성
        //잘못된 구현을 다소 늦게 발견할 가능성

        //선 테스트 작성, 후 기능 구현
        //복잡도가 낮은, 테스트 가능한 코드로 구현할 수 있게 한다.
        //쉽게 발견하기 어려운 엣지 케이스를 놓치지 않게 해준다.
        //구현에 대한 빠른 피드백을 받을 수 있다.
        //과감한 리팩토링이 가능해진다.

        //TDD를 사용하면 테스트와 상호 작용하며 발전하지만
        //TDD를 하지 않으면 테스트를 검증을 위한 보조수단으로만 생각하게 된다.
        //클라이언트 관점에서의 피드백을 주는 Test Driven

        CafeKiosk cafeKiosk = new CafeKiosk();
        Latte latte = new Latte();
        Americano americano = new Americano();

        cafeKiosk.add(latte);
        cafeKiosk.add(americano);

        int totalPrice = cafeKiosk.calculateTotalPrice();
        assertThat(totalPrice).isEqualTo(8500);
    }

    @Test
    void createOrder() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        cafeKiosk.add(americano);

        Order order = cafeKiosk.createOrder();

        assertThat(order.getBeverages()).hasSize(1);
        assertThat(order.getBeverages().get(0).getName()).isEqualTo("아메리카노");
    }

    @Test
    void createOrderWithCurrentTime() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        cafeKiosk.add(americano);

        Order order = cafeKiosk.createOrder(LocalDateTime.of(2023,6,20,10,0));

        assertThat(order.getBeverages()).hasSize(1);
        assertThat(order.getBeverages().get(0).getName()).isEqualTo("아메리카노");
    }

    @Test
    void createOrderOutsideOpenTime() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        cafeKiosk.add(americano);

        //테스트하기 어려운 영역
        //관측할 때마다 다른 값에 의존하는 코드 -> 현재 날짜/시간, 랜덤 값, 전역 변수/함수, 사용자 입력 등
        //외부 세계에 영향을 주는 코드 -> 표준 출력, 메시지 발송, 데이터베이스에 기록하기 등

        assertThatThrownBy(() -> cafeKiosk.createOrder(LocalDateTime.of(2023,6,20,9,59)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}