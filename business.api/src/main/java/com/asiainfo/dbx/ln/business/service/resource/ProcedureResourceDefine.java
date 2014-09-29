package com.asiainfo.dbx.ln.business.service.resource;

import com.asiainfo.dbx.ln.component.util.AppStringUtils;

/**
 * Created by yanlei on 2014/9/22.
 */
public class ProcedureResourceDefine implements ResourceDefine {
    String resourceType= ResourceDefine.RESOURCE_TYPE_DATA;
    String repository;
    String container;
    String procedureName;


    public ProcedureResourceDefine(String repository, String container, String procedureName) {
        this.repository = repository;
        this.container = container;
        this.procedureName = procedureName;
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

    public String getProcedureName() {
        return procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
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

        ProcedureResourceDefine that = (ProcedureResourceDefine) o;
        if (resourceType != null ? !resourceType.equals(that.resourceType) : that.resourceType != null) return false;
        if (repository != null ? !repository.equals(that.repository) : that.repository != null) return false;

        if (container != null ? !container.equals(that.container) : that.container != null) return false;
        if (procedureName != null ? !procedureName.equals(that.procedureName) : that.procedureName != null) return false;


        return true;
    }

    @Override
    public boolean matchResource(ResourceDefine o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProcedureResourceDefine that = (ProcedureResourceDefine) o;
        if (resourceType != null && !AppStringUtils.matchFullString(resourceType,that.resourceType)) return false;
        if (repository != null && !AppStringUtils.matchFullString(repository,that.repository)) return false;
        if (container != null && !AppStringUtils.matchFullString(container,that.container)) return false;
        if (procedureName != null && !AppStringUtils.matchFullString(procedureName,that.procedureName)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = resourceType != null ? resourceType.hashCode() : 0;
        result = 31 * result + (repository != null ? repository.hashCode() : 0);
        result = 31 * result + (container != null ? container.hashCode() : 0);
        result = 31 * result + (procedureName != null ? procedureName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DataResourceDefine{");
        sb.append("resourceType='").append(resourceType).append('\'');
        sb.append(", repository='").append(repository).append('\'');
        sb.append(", container='").append(container).append('\'');
        sb.append(", procedureName='").append(procedureName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
