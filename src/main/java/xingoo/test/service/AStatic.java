package xingoo.test.service;

import org.springframework.stereotype.Service;

@Service
public class AStatic {

    public static void hello(String name){
        System.out.println("hello, "+name);
    }
}
