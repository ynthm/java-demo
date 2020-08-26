package com.ynthm.tools.domain;

/**
 * Author : Ynthm
 */
public class Book implements Comparable<Book> {
    private String name;
    private int count;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public int compareTo(Book book) {
        int result;
        result = this.count - book.getCount();
        if (result == 0) {
            result = this.name.compareTo(book.getName());
        }

        return result;
    }
}
