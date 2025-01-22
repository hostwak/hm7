package hw16;

import hw16.DBConnect;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Cache {
    Class<DBConnect> value();
}