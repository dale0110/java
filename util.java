package kevin.reflect;
import java.lang.reflect.*;

public class util {
	/*
	 * 打印类信息
	 */
	public static void printClassInfo(Object obj){
		Class<?> objClass=obj.getClass();
		System.out.println("class name:"+objClass.getName());
		
		Class<?> objSuperClass=objClass.getSuperclass();
		System.out.println("super class name:"+objSuperClass.getName());
	}
	
	
	/*
	 * 打印类属性
	 */
	public static void printFiledInfo(Object obj){
		Class<?> objClass=obj.getClass();
		
		
        // 取得本类的全部属性
        Field[] field = objClass.getDeclaredFields();
        for (int i = 0; i < field.length; i++) {
            // 权限修饰符
            int mo = field[i].getModifiers();
            String priv = Modifier.toString(mo);
            // 属性类型
            Class<?> type = field[i].getType();
            System.out.println(priv + " " + type.getName() + " "
                    + field[i].getName() + ";");
        }
        System.out.println("===============实现的接口或者父类的属性========================");
        // 取得实现的接口或者父类的属性
        Field[] filed1 = objClass.getFields();
        for (int j = 0; j < filed1.length; j++) {
            // 权限修饰符
            int mo = filed1[j].getModifiers();
            String priv = Modifier.toString(mo);
            // 属性类型
            Class<?> type = filed1[j].getType();
            System.out.println(priv + " " + type.getName() + " "
                    + filed1[j].getName() + ";");
        }
	}

	/*
	 * 打印类属性
	 */
	public static void printConstructInfo(Object obj){
		
		Class<?> objClass=obj.getClass();
		
        
		Method method[]=objClass.getMethods();
        for(int i=0;i<method.length;++i){
            Class<?> returnType=method[i].getReturnType();
            Class<?> para[]=method[i].getParameterTypes();
            int temp=method[i].getModifiers();
            System.out.print(Modifier.toString(temp)+" ");
            System.out.print(returnType.getName()+"  ");
            System.out.print(method[i].getName()+" ");
            System.out.print("(");
            for(int j=0;j<para.length;++j){
                System.out.print(para[j].getName()+" "+"arg"+j);
                if(j<para.length-1){
                    System.out.print(",");
                }
            }
            Class<?> exce[]=method[i].getExceptionTypes();
            if(exce.length>0){
                System.out.print(") throws ");
                for(int k=0;k<exce.length;++k){
                    System.out.print(exce[k].getName()+" ");
                    if(k<exce.length-1){
                        System.out.print(",");
                    }
                }
            }else{
                System.out.print(")");
            }
            System.out.println();
        }
	}
	
	/*
	 * 当前的堆栈
	 */
	public static void printStackinfo(){
		Throwable ex = new Throwable();
        StackTraceElement[] stackElements = ex.getStackTrace();

        if (stackElements != null) {
            for (int i = 0; i < stackElements.length; i++) {
                System.out.println(stackElements[i].getClassName());//返回类的完全限定名，该类包含由该堆栈跟踪元素所表示的执行点。
                System.out.println(stackElements[i].getFileName());//返回源文件名，该文件包含由该堆栈跟踪元素所表示的执行点。
                System.out.println(stackElements[i].getLineNumber());//返回源行的行号，该行包含由该堆栈该跟踪元素所表示的执行点。
                System.out.println(stackElements[i].getMethodName());//返回方法名，此方法包含由该堆栈跟踪元素所表示的执行点。
                System.out.println("-------------第"+i+"级调用-------------------");
            }
        }
	}

}
