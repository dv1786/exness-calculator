package com.exness;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class ApiTestRunner {

    public static void main(String[] args)
    {

        Result res = JUnitCore.runClasses(TestAccountTypeMini.class,TestAccountTypeClassic.class,TestAccountTypeCent.class, TestAccountTypeECN.class);

        for(Failure fail: res.getFailures())
        {
            System.err.print(fail.getTrace() + ", " + fail.getMessage());
        }

        if(res.wasSuccessful()) System.out.println("Run is Ok!!!");
        else{
            System.out.println("smth wrong.. see info above..");
        }

    }


}
