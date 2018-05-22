package com.itdoc.pd;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

/**
 * 流程定义
 *
 * 1.部署
 * 2.查询部署
 * 3.查询图片
 * 4.删除部署
 */
public class PdTest {

    /**
     * 通过classpath正常部署
     *
     * 涉及到的表
     *  act_ge_bytearray:
     *  1.英文解释
     *  act:activiti
     *  ge:general
     *  bytearray:二进制
     *  2.字段
     *  name_:文件的路径加上名称
     *  bytes_:存放内容
     *  deployment_id_:部署ID
     *  3.说明：
     *  如果要查询文件(bpmn和png)，需要知道deploymentId
     *  act_re_deployment
     *  1.解析
     *  re:repository
     *  deployment:部署  用户描述一次部署
     *  2.字段
     *  ID_：部署ID  主键
     *  act_re_procdef
     *  1.解释
     *  procdef: process definition  流程定义
     *  2.字段
     *  id_称为pdid:pd:+version:+随机数
     *  name:名称
     *  key:名称
     *  version:版本号
     *  如果名称不变，每次部署，版本号加1
     *  如果名称改变，则版本号从1开始计算
     *  deployment_id_:部署ID
     */
    @Test
    public void testDeployFromClasspath() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getRepositoryService()
                .createDeployment()
                .name("请假流程")
                .addClasspathResource("qingjia.bpmn")
                .deploy();
    }

    /**
     * 通过 inputStream 部署
     */
    @Test
    public void testDeployFromInputStream() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("qingjia.bpmn");
        processEngine.getRepositoryService().createDeployment()
                .addInputStream("qingjia.bpmn", inputStream)
                .deploy();
    }

    /**
     * 通过 zipInputStream 部署
     */
    @Test
    public void testDeployFromZipInputStream() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        InputStream input = this.getClass().getClassLoader().getResourceAsStream("qingjia.bpmn");
        ZipInputStream zipInputStream = new ZipInputStream(input);
        processEngine.getRepositoryService().createDeployment()
                .addZipInputStream(zipInputStream)
                .deploy();
    }

    /**
     * 删除部署的任务
     */
    @Test
    public void testDelete() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getRepositoryService()
                .deleteDeployment("1", true);
    }

    /**
     * 查询全部部署
     */
    @Test
    public void queryAllDeploy() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        List<Deployment> deploymentList = processEngine.getRepositoryService()
                .createDeploymentQuery()
                // 按照部署时间降序
                .orderByDeploymenTime()
                .desc()
                .list();
        for (Deployment deployment : deploymentList) {
            System.out.println(deployment.getId() + " -- " + deployment.getName());
        }
    }

    /**
     * 根据名称查询全部部署
     */
    @Test
    public void queryAllDeployByName() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        List<Deployment> deploymentList = processEngine.getRepositoryService()
                .createDeploymentQuery()
                // 按照部署时间降序
                .orderByDeploymenTime()
                .desc()
                .deploymentName("请假流程")
                .list();
        for (Deployment deployment : deploymentList) {
            System.out.println(deployment.getId() + " -- " + deployment.getName());
        }
    }


    /**
     * 查询所有的流程定义
     */
    @Test
    public void queryAllProcessDefinition() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        List<ProcessDefinition> processDefinitionList = processEngine.getRepositoryService()
                .createProcessDefinitionQuery()
                .orderByProcessDefinitionVersion()
                .desc()
                .list();
        for (ProcessDefinition processDefinition : processDefinitionList) {
            System.out.println(processDefinition.getId() + " -- " + processDefinition.getVersion());
        }
    }

    /**
     * 根据deploymentId和name查看流程图
     * @throws Exception
     */
    @Test
    public void showImage() throws Exception {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        InputStream inputStream = processEngine.getRepositoryService()
                .getResourceAsStream("103", "qingjia.png");
        OutputStream outputStream = new FileOutputStream("e:/qingjia.png");
        for (int in = -1; (in = inputStream.read()) != -1; ) {
            outputStream.write(in);
        }

        inputStream.close();
        outputStream.close();
    }

    /**
     * 根据pdid查询任务流程图
     * @throws Exception
     */
    @Test
    public void showImage2() throws Exception {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        InputStream inputStream = processEngine.getRepositoryService()
                .getProcessDiagram("qingjia:2:104");
        OutputStream outputStream = new FileOutputStream("e:/qingjia.png");
        for (int in = -1; (in = inputStream.read()) != -1; ) {
            outputStream.write(in);
        }

        inputStream.close();
        outputStream.close();
    }

    /**
     * 查看bpmn文件
     * @throws Exception
     */
    @Test
    public void showBpmn() throws Exception {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        InputStream inputStream = processEngine.getRepositoryService()
                .getProcessModel("qingjia:2:104");
        OutputStream outputStream = new FileOutputStream("e:/qingjia.bpmn");
        for (int in = -1; (in = inputStream.read()) != -1; ) {
            outputStream.write(in);
        }

        inputStream.close();
        outputStream.close();
    }


}
