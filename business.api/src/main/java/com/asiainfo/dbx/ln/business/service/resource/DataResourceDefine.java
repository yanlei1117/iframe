package com.asiainfo.dbx.ln.business.service.resource;

import com.asiainfo.dbx.ln.component.util.AppStringUtils;

/**
 * Created by yanlei on 2014/9/22.
 */
public class DataResourceDefine implements ResourceDefine {
    String resourceType= ResourceDefine.RESOURCE_TYPE_DATA;
    String repository;
    String container;
    String collection;

    private ThreadLocal<String> itemIdThreadLocal = new ThreadLocal<String>();

    public DataResourceDefine(String repository, String container, String collection) {
        this.repository = repository!= null?repository.toLowerCase():null;
        this.container = container!=null?container.toLowerCase():null;
        this.collection = collection!=null?collection.toLowerCase():null;
    }

    public String getItemId() {
        return itemIdThreadLocal.get();
    }

    public void setItemId(String itemId) {
        itemIdThreadLocal.set(itemId);
    }

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

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    @Override
    public String getResourceType() {
        return this.resourceType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataResourceDefine that = (DataResourceDefine) o;
        if (resourceType != null ? !resourceType.equals(that.resourceType) : that.resourceType != null) return false;
        if (repository != null ? !repository.equals(that.repository) : that.repository != null) return false;

        if (container != null ? !container.equals(that.container) : that.container != null) return false;
        if (collection != null ? !collection.equals(that.collection) : that.collection != null) return false;


        return true;
    }

    @Override
    public boolean matchResource(ResourceDefine o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataResourceDefine that = (DataResourceDefine) o;
        if (resourceType != null && !AppStringUtils.matchFullString(resourceType,that.resourceType)) return false;
        if (repository != null && !AppStringUtils.matchFullString(repository,that.repository)) return false;
        if (container != null && !AppStringUtils.matchFullString(container,that.container)) return false;
        if (collection != null && !AppStringUtils.matchFullString(collection,that.collection)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = resourceType != null ? resourceType.hashCode() : 0;
        result = 31 * result + (repository != null ? repository.hashCode() : 0);
        result = 31 * result + (container != null ? container.hashCode() : 0);
        result = 31 * result + (collection != null ? collection.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DataResourceDefine{");
        sb.append("resourceType='").append(resourceType).append('\'');
        sb.append(", repository='").append(repository).append('\'');
        sb.append(", container='").append(container).append('\'');
        sb.append(", collection='").append(collection).append('\'');
        sb.append(", itemId='").append(this.getItemId()).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
