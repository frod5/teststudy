package sample.cafekiosk.unit;

import org.junit.jupiter.api.Test;
import sample.cafekiosk.unit.beverage.Americano;

import static org.junit.jupiter.api.Assertions.*;

class CafeKioskTest {

    @Test
    void add() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());

        //결국 사람이 콘솔로 검증한다. 다른 사람은 어떤게 맞는 건지 알 수 없다.
        //무조건 성공하는 코드
        System.out.println(">>> 담긴 음료 수 : "+ cafeKiosk.getBeverages().size());
        System.out.println(">>> 담긴 음료 : "+ cafeKiosk.getBeverages().get(0).getName());
    }

}