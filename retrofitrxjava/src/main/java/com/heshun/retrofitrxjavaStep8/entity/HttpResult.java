package com.heshun.retrofitrxjavaStep8.entity;

/**
 * Created by liukun on 16/3/5.
 */
public class HttpResult<T> {

    private int count;
    private int start;
    private int total;
    private String title;

    //用来模仿Data
    private T movies;


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public T getMovies() {
        return movies;
    }

    public void setMovies(T movies) {
        this.movies = movies;
    }


    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("title=" + title + " count=" + count + " start=" + start);
        if (null != movies) {
            sb.append(" movies:" + movies.toString());
        }
        return sb.toString();
    }
}
