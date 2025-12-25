package com.phucnguyen.section10.assignment.buffer;

import com.phucnguyen.common.Util;

public record BookOrder(String genre,
                        String title,
                        Integer price) {

    public static BookOrder create(){
        var book = Util.getFaker().book();
        return new BookOrder(
                book.genre(),
                book.title(),
                Util.getFaker().random().nextInt(10, 100)
        );
    }

}