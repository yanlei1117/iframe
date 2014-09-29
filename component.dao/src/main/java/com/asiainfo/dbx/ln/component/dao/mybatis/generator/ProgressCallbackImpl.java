package com.asiainfo.dbx.ln.component.dao.mybatis.generator;

import org.mybatis.generator.api.ProgressCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by yanlei on 2014/8/12.
 */
public class ProgressCallbackImpl implements ProgressCallback {
    private final Logger logger = LoggerFactory.getLogger(ProgressCallbackImpl.class);
    @Override
    public void introspectionStarted(int totalTasks) {
        logger.info("introspectionStarted(totalTask:{})",totalTasks);
    }

    @Override
    public void generationStarted(int totalTasks) {
        logger.info("generationStarted(totalTask:{})",totalTasks);
    }

    @Override
    public void saveStarted(int totalTasks) {
        logger.info("saveStarted(totalTask:{})",totalTasks);
    }

    @Override
    public void startTask(String taskName) {
        logger.info("startTask(totalTask:{})",taskName);
    }

    @Override
    public void done() {
        logger.info("generate mybatis file ok.");
    }

    @Override
    public void checkCancel() throws InterruptedException {

    }
}
