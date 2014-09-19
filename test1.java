/*class persson{
	int id;
	String name;
	int age;
	int weight;
	
	public void person()
	{
	
	}
	
}*/

class A
{
	public void func1()
	{
		System.out.println("A func1");
	}
	
	public void func2()
	{
		System.out.println("A func2");
		func1();
	}
	
}

class B extends A
{
	public void func1()
	{
		System.out.println("B func1");
		//stack.print();
	}
	
	public void func2()
	{
		System.out.println("B func2");
	}
}


class stack
{
	public static void print()
	{
		Throwable ex = new Throwable();
        StackTraceElement[] stackElements = ex.getStackTrace();

        if (stackElements != null) {
            System.out.println("print stack begin:");
            for (int i = 0; i < stackElements.length; i++) {
                System.out.println("class:"+stackElements[i].getClassName()+
                		" file:"+stackElements[i].getFileName()+
                		" line:"+stackElements[i].getLineNumber()+
                		" func:"+stackElements[i].getMethodName());//返回方法名，此方法包含由该堆栈跟踪元素所表示的执行点。
            }
            System.out.println("print stack end");
        }
	}
}

class C
{
	public static void main(String [] args)
	{
		B b=new B();
		A a=b;
        System.out.println("print b:");	
		callA(b);
        System.out.println("print a:");	
		callA(a);
        System.out.println("print c:");	
		A c=new A();
		callA(c);
	}
	
	public static void callA(A a)
	{
		a.func1();
		a.func2();
	}

}
