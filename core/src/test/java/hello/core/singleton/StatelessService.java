package hello.core.singleton;

public class StatelessService {

//    private int price; // 상태를 유지하는 필드를 없앰.

    public int order(String name, int price) {
        System.out.println("name = " + name + " price = " + price);

        return price;   // 바로 받은 값을 반환하게 함.
    }

//    public int getPrice() {
//        return price;
//    }

}
// 무상태로 설계하는 방법