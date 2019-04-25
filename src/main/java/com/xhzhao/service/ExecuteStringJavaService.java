package com.xhzhao.service;

import com.xhzhao.compiler.CustomStringJavaCompiler;
import com.xhzhao.executor.JavaClassExecutor;
import org.springframework.stereotype.Service;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaFileObject;
import java.util.List;
import java.util.concurrent.*;

@Service
public class ExecuteStringJavaService {
    /* 客户端发来的程序的运行时间限制 */
    private static final int RUN_TIME_LIMITED = 15;

    /* N_THREAD = N_CPU + 1，因为是 CPU 密集型的操作 */
    private static final int N_THREAD = 5;

    /* 负责执行客户端代码的线程池，根据《Java 开发手册》不可用 Executor 创建，有 OOM 的可能 */
    private static final ExecutorService pool = new ThreadPoolExecutor(N_THREAD, N_THREAD,
            60L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(N_THREAD));

    private static final String WAIT_WARNING = "服务器忙，请稍后提交";
    private static final String NO_OUTPUT = "Nothing.";

    public String execute(String source){
        //编译结果收集器
        DiagnosticCollector<JavaFileObject> collector = new DiagnosticCollector<>();
        byte[] classBytes = CustomStringJavaCompiler.compile(source, collector);

        //1.编译不通过
        if (classBytes == null){
            //获取编译错误
            List<Diagnostic<? extends JavaFileObject>> compileError = collector.getDiagnostics();
            StringBuilder compileErrorRes = new StringBuilder();
            for (Diagnostic diagnostic:
                 compileError) {
                compileErrorRes.append("Compilation error at: ");
                compileErrorRes.append(diagnostic.getLineNumber());
                compileErrorRes.append(". ");
                compileErrorRes.append(System.lineSeparator());
            }
            return compileErrorRes.toString();
        }

        //2.编译通过，运行main方法执行字节码
        Callable<String> runTask = new Callable<String>() {
            @Override
            public String call() throws Exception {
                return JavaClassExecutor.execute(classBytes);
            }
        };

        Future<String> res = null;
        try {
            res = pool.submit(runTask);
        }catch (RejectedExecutionException ex){
            return WAIT_WARNING;
        }

        //获取运行结果，处理非客户端代码错误
        String runResult;
        try{
            runResult = res.get(RUN_TIME_LIMITED, TimeUnit.SECONDS);
        }catch (InterruptedException ex){
            runResult = "Program interrupted";
        }catch (ExecutionException ex){
            runResult = ex.getCause().getMessage();
        }catch (TimeoutException ex){
            runResult = "Time Limit Exceeded";
        }finally {
            res.cancel(true);
        }

        return runResult != null ? runResult : NO_OUTPUT;
    }
}
