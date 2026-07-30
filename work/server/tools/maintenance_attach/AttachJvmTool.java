package maint;

import com.sun.tools.attach.VirtualMachine;

public final class AttachJvmTool {

    private AttachJvmTool() {
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 2 || args.length > 3) {
            System.err.println("Usage: AttachJvmTool <pid> <agent-jar> [command]");
            System.exit(1);
            return;
        }

        String pid = args[0];
        String agentJar = args[1];
        String command = args.length >= 3 ? args[2] : "stop";

        System.out.println("[attach-tool] attaching to pid " + pid);
        VirtualMachine vm = VirtualMachine.attach(pid);
        try {
            vm.loadAgent(agentJar, command);
            System.out.println("[attach-tool] agent loaded with command=" + command);
        } finally {
            vm.detach();
            System.out.println("[attach-tool] detached");
        }
    }
}
