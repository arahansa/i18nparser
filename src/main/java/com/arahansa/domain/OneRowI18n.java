package com.arahansa.domain;

import lombok.Data;

/**
 * Created by arahansa on 2015-11-10.
 */

@Data
public class OneRowI18n {
    private int num;
    private String kor;
    private String eng;

    public OneRowI18n(String kor, String eng) {
        this.kor = kor;
        this.eng = eng;
    }

    public OneRowI18n(int num, String kor, String eng) {
        this.num = num;
        this.kor = kor;
        this.eng = eng;
    }

}
