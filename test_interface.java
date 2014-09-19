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


interface PCI
{
 void start();
 void end();
}


class SoundCard implements PCI
{
	String name;
	
	public SoundCard(String name)
	{
		this.name=name;
	}
	
	public void start()
	{
		stack.print();
	}
	
	public void end()
	{
		stack.print();
	}
	
}

class NetCard implements PCI
{
	String name;
	
	public NetCard(String name)
	{
		this.name=name;
	}
	
	public void start()
	{
		stack.print();
	}
	
	public void end()
	{
		stack.print();
	}
}

class MainBoard
{
	String name;
	
	public MainBoard(String name)
	{
		this.name=name;
	}	
	
	public void UsePCICard(PCI card)
	{
		card.start();
		card.end();
	}
}

class Assembler
{
	public static void main(String [] args)
	{
		MainBoard mb=new MainBoard("HS");

		NetCard nc= new NetCard("jj");
		mb.UsePCICard(nc);
		
		SoundCard sc = new SoundCard("lj");
		mb.UsePCICard(sc);
	}
}
