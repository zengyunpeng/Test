package com.intellif.test.dagger;

import java.util.ArrayList;
import java.util.List;

public class B<T extends Object> {
    List<? super Object> list = new ArrayList<>();
}
