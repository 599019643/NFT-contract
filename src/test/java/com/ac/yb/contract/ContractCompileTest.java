package com.ac.yb.contract;

import com.webank.solc.plugin.config.SolidityCompileExtensions;
import com.webank.solc.plugin.handler.CompileHandler;
import org.junit.Test;

import java.io.File;

/**
 * 合约编译测试
 * @author maochaowu
 * @date 2023/4/19 14:46
 */
public class ContractCompileTest {

    @Test
    public void testSelector() throws Exception {
        String projectHome = System.getProperty("user.dir");
        File projectDir = new File(projectHome);

        SolidityCompileExtensions extensions = new SolidityCompileExtensions();
        extensions.setPkg("com.ac.yb.contracts");
        /**设置要编译的合约名*/
        extensions.setSelector("NoLimitToken.sol");

        CompileHandler compileHandler = new CompileHandler(projectDir, extensions);
        compileHandler.doSolc();
    }
}
