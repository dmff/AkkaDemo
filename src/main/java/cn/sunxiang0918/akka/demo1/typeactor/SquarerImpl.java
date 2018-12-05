package cn.sunxiang0918.akka.demo1.typeactor;

import akka.dispatch.Futures;
import akka.japi.Option;
import scala.concurrent.Future;

/**
 * @author SUN
 * @version 1.0
 * @Date 16/1/24 13:55
 */
public class SquarerImpl implements Squarer {

    private String name;

    public SquarerImpl() {
        this.name = "default";
    }

    public SquarerImpl(String name) {
        this.name = name;
    }

    @Override
    public Future<Integer> square(int i) {
        return Futures.successful(squareNow(i));
    }

    @Override
    public Option<Integer> squareNowPlease(int i) {
        return Option.some(squareNow(i));
    }

    @Override
    public int squareNow(int i) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("执行里面");
        return i * i;
    }
}
