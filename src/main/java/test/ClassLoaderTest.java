package test;

import java.lang.reflect.Constructor;

public class ClassLoaderTest {
    public static void main(String[] args) throws Exception{
        CustomClassloader ccl = new CustomClassloader();
        ccl.loadClass("test.RefA");
    }
}
