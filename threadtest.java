class stack
{
	public static void print()
	{
		Throwable ex = new Throwable();
        StackTraceElement[] stackElements = ex.getStackTrace();

        if (stackElements != null) {
            System.out.println("*******************************:");
            for (int i = 0; i < stackElements.length; i++) {
                System.out.println("class:"+stackElements[i].getClassName()+
                		" file:"+stackElements[i].getFileName()+
                		" line:"+stackElements[i].getLineNumber()+
                		" func:"+stackElements[i].getMethodName());//返回方法名，此方法包含由该堆栈跟踪元素所表示的执行点。
            }
            System.out.println("*******************************");
        }
	}
}


class MyTestThreaed implements Runnable
{
	private  int  counter=1000;
	//int  counter=10000;
	public void run()
	{
		for(;counter>=0;counter--)
		{
		    System.out.println(Thread.currentThread().getName()+" :"+counter); 
		    //System.out.println(Thread.currentThread().getStackTrace()[2].getClassName());
		    //System.out.println("counter"+counter);
		}
	}
}

class ThreadDemo
{
	public static void main(String [] args)
	{
		MyTestThreaed tt=new MyTestThreaed();
		
		for(int i=0;i<100;i++)
		{
			new Thread(tt).start();
			//Thread nt=new Thread(tt);
			//nt.start();
		}
		
		for(int i=1000; i<2000; i++)
		{
		    System.out.println(Thread.currentThread().getName()+" :"+i); 
		}
		
	}
}
