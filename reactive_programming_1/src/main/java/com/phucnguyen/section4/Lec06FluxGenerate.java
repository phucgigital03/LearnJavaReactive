package com.phucnguyen.section4;

/*
    Flux generate
    - invokes the given lambda expression again and again based on downstream demand.
    - We can emit only one value at a time
    - will stop when complete method is invoked
    - will stop when error method is invoked
    - will stop downstream cancels
*/
import com.phucnguyen.common.Util;
import reactor.core.publisher.Flux;

public class Lec06FluxGenerate {

    public static void main(String[] args) {

        Flux.generate(synchronousSink -> {
                    synchronousSink.next(1);
//                    synchronousSink.next(2);
//                    synchronousSink.complete();
                    synchronousSink.error(new RuntimeException("oops"));
                })
                .take(2)
                .subscribe(Util.subscriber());


//      Revise polymorphic and inherit
        Parent child = new Child("abc", 1, true);
        System.out.println("tuoi con: " + child.calculateAge());

        Child child2 = new Child("abc", 1, true);
        System.out.println("con dang hoc: " + child2.isStudy());

//        ***Error when cast from Parent to Child
//        Child child3 = (Child) new Parent("a",1);
//        Child child3 =  new Parent("a",1);
//        System.out.println("con dang hoc: " + child3.calculateAge());
    }
}

class Parent {
    protected String name;
    protected int age;

    public Parent(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public int calculateAge(){
        return age;
    }
}

class Child extends Parent {
    private boolean study;

    public Child(String name, int age, boolean study) {
        super(name, age);
        this.study = study;
    }

    public boolean isStudy() {
        return study;
    }
}