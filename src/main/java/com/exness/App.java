package com.exness;
import static org.assertj.core.api.Assertions.*;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        v();

    }

    public static void v (){
      assertThat("ignore").hasSize(4);
    }
}
