package test;

public class TestMain {
    public static void main(String[] args) throws Exception{

        TestClass org = new TestClass();
        System.out.println("normal class : "+org);

        CustomClassLoader ccl = new CustomClassLoader();
        Class cl = ccl.findClass("test.TestClass");
        System.out.println("classLoader class : "+cl.getDeclaredConstructor().newInstance());
//        TestClass custom = (TestClass)cl.getDeclaredConstructor().newInstance();
//        System.out.println("custom : "+custom.getAaa());

        /*
        CustomClassLoader ccl = new CustomClassLoader();
        TestClass testClass = (TestClass)
            ccl.loadClass("test.TestClass").getDeclaredConstructor().newInstance();

        System.out.println(testClass.getAaa());

         */
    }
}
