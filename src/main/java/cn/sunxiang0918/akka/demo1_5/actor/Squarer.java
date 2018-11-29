package cn.sunxiang0918.akka.demo1_5.actor;

import akka.japi.Option;
import scala.concurrent.Future;

/**
 * @author SUN
 * @version 1.0
 * @Date 16/1/24 13:53
 */
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
