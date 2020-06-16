package com.kayeez.command4j.command;

import java.util.Objects;

/**
 * @author: zhaokai
 * @create: 2018-09-05 18:22:12
 */
public abstract class AbstractCommand {
    protected static final String DELIMITER = ";";
    private boolean withSudo;

    public boolean isWithSudo() {
        return withSudo;
    }

    public void setWithSudo(boolean withSudo) {
        this.withSudo = withSudo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AbstractCommand that = (AbstractCommand) o;
        return withSudo == that.withSudo;
    }

    @Override
    public int hashCode() {

        return Objects.hash(withSudo);
    }

    /**
     * 构建命令
     *
     * @return
     */
    public abstract String buildRunCmd();

    public final String buildRunCmdWithSudo(String password) {
        String runCmd = buildRunCmd();
        if (runCmd == null || "".equals(runCmd)) {
            return "";
        }
        String[] cmds = runCmd.trim().split(DELIMITER);
        StringBuilder recreated = new StringBuilder();


        for (String c : cmds) {
            if (c.trim().startsWith("then") || c.trim().startsWith("else")) {
                //if 的执行逻辑 加sudo 在then后面，如 then bash -c "echo '123' > /root/abc" 替换为 then echo 密码 | sudo -S bash -c "echo '123' /root/abc"
                recreated.append(c.replaceAll("then ", "then echo " + password + " | sudo -S ")
                        .replaceAll("else ", "else echo " + password + " | sudo -S ")).append(" ").append(DELIMITER);
            } else if (c.trim().startsWith("if [") || c.trim().equals("fi")) {
                // if条件判断，和结束 不用加sudo
                recreated.append(c).append(" ").append(DELIMITER);
            } else if (c.trim().startsWith("source ")) {
                // if条件判断，和结束 不用加sudo
                recreated.append(c).append(" ").append(DELIMITER);
            } else {
                // 通用命令
                recreated.append("echo ").append(password).append(" | sudo -S ").append(c).append(DELIMITER);
            }

        }
        // 与符号处理
        String r = recreated.toString().replaceAll("&&", "&& echo " + password + " | sudo -S ");
        // 去除最后一个分号
        r = r.substring(0, r.length() - 1);

        return r;
    }


}
