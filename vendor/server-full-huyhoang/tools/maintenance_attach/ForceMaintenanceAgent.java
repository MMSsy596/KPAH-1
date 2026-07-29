package maint;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class ForceMaintenanceAgent {

    private ForceMaintenanceAgent() {
    }

    public static void premain(String agentArgs, Instrumentation instrumentation) {
        run(agentArgs);
    }

    public static void agentmain(String agentArgs, Instrumentation instrumentation) {
        run(agentArgs);
    }

    private static void run(String agentArgs) {
        String command = agentArgs == null ? "stop" : agentArgs.trim().toLowerCase();
        if (command.isEmpty()) {
            command = "stop";
        }

        System.out.println("[maintenance-agent] command=" + command);

        try {
            switch (command) {
                case "save":
                    invokeSave(false);
                    break;
                case "freeze-save":
                    invokeSave(true);
                    break;
                case "restart":
                    invokeShutdown(true);
                    break;
                case "shutdown":
                case "stop":
                default:
                    invokeStop();
                    break;
            }
        } catch (Throwable throwable) {
            System.err.println("[maintenance-agent] failed: " + throwable.getMessage());
            throwable.printStackTrace();
        }
    }

    private static void invokeSave(boolean maintenanceMode) throws Exception {
        Class<?> realControllerClass = Class.forName("real.RealController");
        Field instanceField = realControllerClass.getField("intance");
        Object controller = instanceField.get(null);
        if (controller == null) {
            throw new IllegalStateException("real.RealController.intance is null");
        }
        Method saveAllChar = realControllerClass.getMethod("saveAllChar", boolean.class);
        saveAllChar.invoke(controller, maintenanceMode);
        System.out.println("[maintenance-agent] saveAllChar(" + maintenanceMode + ") done");
    }

    private static void invokeStop() throws Exception {
        Class<?> adminHandlerClass = Class.forName("real.AdminHandler");
        Object adminHandler = adminHandlerClass.getConstructor().newInstance();
        Method stopServer = adminHandlerClass.getMethod("stopServer");
        stopServer.invoke(adminHandler);
        System.out.println("[maintenance-agent] stopServer() invoked");
    }

    private static void invokeShutdown(boolean scheduleRestart) throws Exception {
        Class<?> adminHandlerClass = Class.forName("real.AdminHandler");
        Object adminHandler = adminHandlerClass.getConstructor().newInstance();
        Method shutdownServer = adminHandlerClass.getMethod("shutdownServer", boolean.class);
        shutdownServer.invoke(adminHandler, scheduleRestart);
        System.out.println("[maintenance-agent] shutdownServer(" + scheduleRestart + ") invoked");
    }
}
