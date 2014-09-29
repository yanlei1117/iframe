package com.asiainfo.dbx.ln.business.runner.runner.das;

import com.asiainfo.dbx.ln.business.runner.Runner;

/**
 * Created by yanlei on 2014/9/20.
 */
public  abstract  class DasRunner extends Runner {
    String repository;
    String container;
    String collection;
    public String getRepository() {
        return repository;
    }

    public void setRepository(String repository) {
        this.repository = repository;
    }

    public String getContainer() {
        return container;
    }

    public void setContainer(String container) {
        this.container = container;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DasCreateRunner{");
        sb.append("repository='").append(repository).append('\'');
        sb.append(", container='").append(container).append('\'');
        sb.append(", collection='").append(collection).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
