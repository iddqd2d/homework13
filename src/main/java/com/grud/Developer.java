package com.grud;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class Developer {
    private String name;
    private int age;
    private boolean sex;
    private int salary;
}
