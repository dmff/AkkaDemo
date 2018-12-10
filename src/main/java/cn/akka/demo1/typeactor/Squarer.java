package cn.akka.demo1.typeactor;

import akka.japi.Option;
import scala.concurrent.Future;


public interface Squarer {

    /**
     * 异步非阻塞
     *
     * @param i
     * @return
     */
    Future<Integer> square(int i);

    /**
     * 异步阻塞
     *
     * @param i
     * @return
     */
    Option<Integer> squareNowPlease(int i);

    /**
     * 同步阻塞
     *
     * @param i
     * @return
     */
    int squareNow(int i);
}
