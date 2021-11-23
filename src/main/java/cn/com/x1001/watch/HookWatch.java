package cn.com.x1001.watch;

import cn.com.x1001.Agent;
import cn.com.x1001.classmap.ClassInfo;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.util.Set;

/**
 * hook点的监控，当发现存在未hook类，自动加入hook
 */
public class HookWatch extends Thread {
    private Instrumentation instrumentation;
    public HookWatch(Instrumentation instrumentation) {
        this.instrumentation = instrumentation;
    }

    @Override
    public void run() {
        while (true) {
            Set<ClassInfo> classHashSet = Agent.context.getClassHashSet();
            for (ClassInfo classInfo:classHashSet){
                if (classInfo.isHooked()) continue;
                try {
                    reTransformClass(classInfo.getClassName());
                } catch (UnmodifiableClassException e) {
                    System.out.println("transform class error:"+e.getMessage());
                }
            }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void reTransformClass(String transformClassName) throws UnmodifiableClassException {
        Class[] loadedClasses = instrumentation.getAllLoadedClasses();
        for (Class clazz : loadedClasses) {
//            if (clazz.isInterface()) continue;
            String className = clazz.getName().replace(".", "/");
            if (className.equals(transformClassName)) {
                instrumentation.retransformClasses(clazz);
            }

        }
    }
}